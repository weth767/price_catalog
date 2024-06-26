package com.jpsouza.webcrawler.crawlers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jpsouza.webcrawler.callables.JSoupCrawlerCallable;
import com.jpsouza.webcrawler.kafka.KafkaProducer;
import com.jpsouza.webcrawler.models.Domain;
import com.jpsouza.webcrawler.models.Link;
import com.jpsouza.webcrawler.services.DomainService;
import com.jpsouza.webcrawler.services.LinkService;
import com.jpsouza.webcrawler.services.QueueService;

@Component
public class JSoupCrawler {
    private static final Logger LOGGER = LogManager.getLogger(JSoupCrawler.class);
    private final DomainService domainService;
    private final LinkService linkService;
    private final KafkaProducer kafkaProducer;
    private ExecutorService executorService;

    public JSoupCrawler(DomainService domainService, LinkService linkService, KafkaProducer kafkaProducer) {
        this.domainService = domainService;
        this.linkService = linkService;
        this.kafkaProducer = kafkaProducer;
    }

    public void startCrawler(int crawlers, Set<String> urls, boolean reset) throws Exception {
        domainService.upsertAll(urls);
        boolean isRunning = Objects.nonNull(QueueService.getInstance().getPolledDomain())
                || !QueueService.getInstance().getQueue().isEmpty();
        if (Objects.isNull(executorService) || !isRunning) {
            executorService = Executors.newFixedThreadPool(crawlers);
        }
        if (reset && !isRunning) {
            linkService.resetAllLinks();
        }
        List<Domain> domainList = domainService.findByUrlInOrderByIdAsc(urls);
        QueueService.getInstance().getQueue().addAll(domainList);
        while (!QueueService.getInstance().getQueue().isEmpty()) {
            Domain domain = QueueService.getInstance().getQueue().poll();
            QueueService.getInstance().setPolledDomain(domain);
            explore(domain.url, domain, domain.url);
        }
        QueueService.getInstance().setPolledDomain(null);
    }

    public void startCompleteCrawling(int crawler) throws Exception {
        boolean isRunning = Objects.nonNull(QueueService.getInstance().getPolledDomain())
                || !QueueService.getInstance().getQueue().isEmpty();
        if (isRunning) {
            List<Domain> domains = domainService.findAll();
            linkService.resetAllLinks();
            QueueService.getInstance().getQueue().addAll(domains);
            while (!QueueService.getInstance().getQueue().isEmpty()) {
                Domain domain = QueueService.getInstance().getQueue().poll();
                QueueService.getInstance().setPolledDomain(domain);
                explore(domain.url, domain, domain.url);
            }
            QueueService.getInstance().setPolledDomain(null);
        }
    }

    private void explore(String url, Domain domain, String filteredText) throws Exception {
        LOGGER.info(String.format("Explorando a url: %s", url));
        Callable<Set<String>> callable = new JSoupCrawlerCallable(url, filteredText, linkService, kafkaProducer);
        Future<Set<String>> future = executorService.submit(callable);
        Set<String> links = future.get();
        Optional<Link> linkOptional = linkService.findByUrl(url);
        if (linkOptional.isPresent()) {
            Link newLink = linkOptional.get();
            newLink.verified = true;
            newLink.verifiedIn = LocalDateTime.now();
            linkService.updateLink(newLink);
        } else {
            linkService.upsertLink(url, domain, true, LocalDateTime.now());
        }
        if (links.isEmpty()) {
            return;
        }
        linkService.upsertLinks(links, domain);
        for (String link : links) {
            explore(link, domain, filteredText);
        }
    }

    public void stopCrawler() {
        if (Objects.nonNull(executorService) && (!executorService.isShutdown() || !executorService.isTerminated())) {
            executorService.shutdown();
            LOGGER.info("Serviço parado");
        }
        QueueService.getInstance().getQueue().clear();
        QueueService.getInstance().setPolledDomain(null);
    }
}
