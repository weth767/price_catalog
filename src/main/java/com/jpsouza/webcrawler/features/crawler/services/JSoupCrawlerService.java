package com.jpsouza.webcrawler.features.crawler.services;

import com.jpsouza.webcrawler.core.models.Domain;
import com.jpsouza.webcrawler.core.models.Link;
import com.jpsouza.webcrawler.core.services.DomainService;
import com.jpsouza.webcrawler.core.services.LinkService;
import com.jpsouza.webcrawler.shared.utils.FormatUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class JSoupCrawlerService {
    private final DomainService domainService;
    private final LinkService linkService;
    private ExecutorService executorService;

    public JSoupCrawlerService(DomainService domainService, LinkService linkService) {
        this.domainService = domainService;
        this.linkService = linkService;
    }

    /**
     * TODO
     * primeiro: preciso criar uma estrutura parental em árvore onde, um link a ser explorado, tem seus filhos,
     * e esses filhos não podem ser iguais aos outros pais ou outros filhos, transformando cada nó em um nó único e
     * não repetitivo.
     * <p>
     * segundo: verificar a possibilidade de utilizar um banco de dados não relacional para esse tipo de validação e
     * armazenamento
     * <p>
     * terceiro: inicializar threads para irem buscando no banco as urls a serem exploradas, threads, na qual serão o
     * números de crawlers
     */
    public void startCrawler(int crawlers, Set<String> urls) throws Exception {
        domainService.upsertAll(urls);
        Optional<Domain> domain = domainService.findNextNotVerifiedDomain();
        if (domain.isEmpty()) {
            throw new Exception("Não há URLs para explorar. Chame o método configure para iniciar a lista de URLs " +
                    "a serem exploradas");
        }
        executorService = Executors.newFixedThreadPool(crawlers);
        explore(domain.get().url, domain.get(), FormatUtils.getDomainName(domain.get().url));
    }

    private void explore(String url, Domain domain, String filteredText) throws Exception {
        Callable<Set<String>> callable = new JSoupCrawlerCallable(url, filteredText, linkService);
        Future<Set<String>> future = executorService.submit(callable);
        Set<String> links = future.get();
        linkService.findByUrl(url).ifPresent((Link link) -> {
            link.verified = true;
            linkService.updateLink(link);
        });
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
        }
    }
}

class JSoupCrawlerCallable implements Callable<Set<String>> {
    private final String url;
    private final String filteredText;
    private final LinkService linkService;

    JSoupCrawlerCallable(String url, String filteredText, LinkService linkService) {
        this.url = url;
        this.filteredText = filteredText;
        this.linkService = linkService;
    }

    @Override
    public Set<String> call() throws Exception {
        if (linkService.existsByUrlAndVerifiedTrue(url)) {
            return new HashSet<>();
        }
        Document document = Jsoup.connect(url).get();
        Elements links = document.select("a[href~=^.*" + filteredText + ".*]");
        return links.stream().map((elementLink) -> elementLink.attr("href")).collect(Collectors.toSet());
    }
}
