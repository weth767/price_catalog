package com.jpsouza.webcrawler.crawler;

import com.jpsouza.webcrawler.callables.JSoupCrawlerCallable;
import com.jpsouza.webcrawler.models.Domain;
import com.jpsouza.webcrawler.models.Link;
import com.jpsouza.webcrawler.services.DomainService;
import com.jpsouza.webcrawler.services.LinkService;
import com.jpsouza.webcrawler.utils.FormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class JSoupCrawlerService {
    private static final Logger LOGGER = LogManager.getLogger(JSoupCrawlerService.class);
    private final DomainService domainService;
    private final LinkService linkService;
    private ExecutorService executorService;

    public JSoupCrawlerService(DomainService domainService, LinkService linkService) {
        this.domainService = domainService;
        this.linkService = linkService;
    }

    /**
     * TODO
     * corrigir a questão de URLs simplificadas que fazem sempre acesso interno ao site, como na kabum,
     * exemplo: /produto/475438/placa-de-video-rx-6750-xt-mech-2x-12g-v1-radeon-12gb-gddr6-freesync-dual-fan,
     * verificar esse tipo de comportamento e acrescentar a url base antes
     */
    public void startCrawler(int crawlers, Set<String> urls) throws Exception {
        domainService.upsertAll(urls);
        executorService = Executors.newFixedThreadPool(crawlers);
        List<Domain> domainList = domainService.findByUrlInOrderByIdAsc(urls);
        for (Domain domain : domainList) {
            explore(domain.url, domain, FormatUtils.getDomainName(domain.url));
        }
    }

    private void explore(String url, Domain domain, String filteredText) throws Exception {
        Callable<Set<String>> callable = new JSoupCrawlerCallable(url, filteredText, linkService);
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
        if (executorService != null && (!executorService.isShutdown() || !executorService.isTerminated())) {
            executorService.shutdown();
            LOGGER.info("Serviço parado");
        }
    }
}

