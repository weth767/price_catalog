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
import com.jpsouza.webcrawler.utils.UrlUtils;

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

    public void startCrawler(int crawlers, Set<String> urls) throws Exception {
        domainService.upsertAll(urls);
        executorService = Executors.newFixedThreadPool(crawlers);
        List<Domain> domainList = domainService.findByUrlInOrderByIdAsc(urls);
        for (Domain domain : domainList) {
            explore(domain.url, domain, domain.url);
        }
    }

    public void startCompleteCrawling(int crawler) throws Exception {
        List<Domain> domains = domainService.findAll();
        linkService.resetAllLinks();
        for (Domain domain : domains) {
            explore(domain.url, domain, UrlUtils.getDomainName(domain.url));
        }
    }

    private void explore(String url, Domain domain, String filteredText) throws Exception {
        LOGGER.info(String.format("Explorando a url: %s", url));
        Callable<Set<String>> callable = new JSoupCrawlerCallable(url, filteredText, linkService, kafkaProducer);
        Future<Set<String>> future = executorService.submit(callable);
        Set<String> links = future.get();
        Optional<Link> linkOptional = linkService.findByUrl(url);
        if (linkOptional.isPresent()) {
            // se o link já foi visto antes, agora que eu tenho todos os links dentro desse
            // site, eu marco ele como verificado
            Link newLink = linkOptional.get();
            newLink.verified = true;
            newLink.verifiedIn = LocalDateTime.now();
            linkService.updateLink(newLink);
        } else {
            // se ainda não foi visto, mas agora que eu tenho todos os link dentro dele, eu
            // salvo ele como visto também
            linkService.upsertLink(url, domain, true, LocalDateTime.now());
        }
        if (links.isEmpty()) {
            return;
        }
        // salva os links encontrados dentro da página da url testada
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
    }
}
