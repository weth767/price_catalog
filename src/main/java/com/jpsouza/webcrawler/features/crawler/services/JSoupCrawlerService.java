package com.jpsouza.webcrawler.features.crawler.services;

import com.jpsouza.webcrawler.core.models.Domain;
import com.jpsouza.webcrawler.core.models.Link;
import com.jpsouza.webcrawler.core.services.DomainService;
import com.jpsouza.webcrawler.core.services.LinkService;
import com.jpsouza.webcrawler.shared.utils.FormatUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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
    public Set<String> call() {
        if (linkService.existsByUrlAndVerifiedTrue(url)) {
            return new HashSet<>();
        }
        try {
            //tratativa para os sites onde os links de produtos, não contém a URL do mesmo
            if (url.startsWith("/") && !url.contains(filteredText)) {
                String newUrl = (filteredText.endsWith("/") ? filteredText.substring(0, filteredText.length() - 1) : filteredText) + url;
                Document document = Jsoup.connect(newUrl).get();
                Elements links = document.select("a[href~=^.*" + filteredText + ".*]");
                return links.stream().map((elementLink) -> elementLink.attr("href")).collect(Collectors.toSet());
            }
            Document document = Jsoup.connect(url).get();
            Elements links = document.select("a[href~=^.*" + filteredText + ".*]");
            return links.stream().map((elementLink) -> elementLink.attr("href")).collect(Collectors.toSet());
        } catch (MalformedURLException malformedURLException) {
            System.out.println("URL mal formada, precisa ter https ou http: " + url);
            return new HashSet<>();
        } catch (HttpStatusException httpStatusException) {
            System.out.println("Página não encontrada");
            return new HashSet<>();
        } catch (UnsupportedMimeTypeException unsupportedMimeTypeException) {
            System.out.println("Tipo de dado não suportado");
            return new HashSet<>();
        } catch (Exception exception) {
            System.out.println("Erro: " + exception.getMessage());
            return new HashSet<>();
        }
    }
}
