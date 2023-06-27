package com.jpsouza.webcrawler.features.crawler.services;

import com.jpsouza.webcrawler.features.exploration.models.Domain;
import com.jpsouza.webcrawler.features.exploration.models.ExploreUrl;
import com.jpsouza.webcrawler.features.exploration.models.Link;
import com.jpsouza.webcrawler.features.exploration.repositories.DomainRepository;
import com.jpsouza.webcrawler.features.exploration.repositories.ExploreUrlRepository;
import com.jpsouza.webcrawler.features.exploration.repositories.LinkRepository;
import com.jpsouza.webcrawler.shared.utils.FormatUtils;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class Crawler4jCrawlerService extends WebCrawler {
    private static final String crawlStorageFolder = "src/main/resources/crawler4j/";
    private static final Pattern FILTERS = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1");
    private final DomainRepository domainRepository;
    private final LinkRepository linkRepository;
    private final ExploreUrlRepository exploreUrlRepository;
    public CrawlController controller;
    public CrawlConfig config;
    public CrawlController.WebCrawlerFactory<Crawler4jCrawlerService> factory;

    public Crawler4jCrawlerService(DomainRepository domainRepository, LinkRepository linkRepository,
                                   ExploreUrlRepository exploreUrlRepository) throws Exception {
        this.domainRepository = domainRepository;
        this.linkRepository = linkRepository;
        this.exploreUrlRepository = exploreUrlRepository;
        config = new CrawlConfig();
        config.setMaxDownloadSize(15000000);
        config.setCrawlStorageFolder(Crawler4jCrawlerService.crawlStorageFolder);
        File folder = new File(config.getCrawlStorageFolder());
        if (!folder.canWrite() || !folder.canRead()) {
            throw new Exception(String.format("Diretório selecionado para o Crawler4J," +
                    " não permite leitura ou escrita: %s", folder.getAbsolutePath()));
        }
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        factory = () -> new Crawler4jCrawlerService(this.domainRepository, this.linkRepository,
                this.exploreUrlRepository);
        controller = new CrawlController(config, pageFetcher, robotstxtServer);
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        super.shouldVisit(referringPage, url);
        return !FILTERS.matcher(href).matches();
        /*if (FILTERS.matcher(href).matches()) {
            return true;
        }
        Optional<ExploreUrl> next = exploreUrlRepository.findFirstByExploredFalseOrderByIdAsc();
        if (next.isPresent()) {
            next.get().explored = true;
            exploreUrlRepository.save(next.get());
        }
        return false;*/
    }

    @Override
    public void visit(Page page) {
        try {
            Optional<Domain> domain = domainRepository.findByUrlLike(FormatUtils
                    .getOnlyDomainFromUrl(page.getWebURL().getURL()));
            Optional<ExploreUrl> next = exploreUrlRepository.findFirstByExploredFalseOrderByIdAsc();
            System.out.println("URL: " + page.getWebURL().getURL());
            next.ifPresent(exploreUrl -> System.out.println("Explored URL: " + exploreUrl.url));
            if (next.isEmpty() ||
                    !page.getWebURL().getURL()
                            .contains(next.get().url)) {
                return;
            }
            if (domain.isPresent()) {
                Link link = new Link();
                link.url = page.getWebURL().getURL();
                link.verified = true;
                link.domain = domain.get();
                linkRepository.save(link);
            } else {
                Domain newDomain = new Domain();
                newDomain.verified = true;
                newDomain.name = FormatUtils.getOnlyDomainFromUrl(page.getWebURL().getURL());
                newDomain.url = FormatUtils.getOnlyDomainFromUrl(page.getWebURL().getURL());
                domainRepository.save(newDomain);
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void configure(Set<String> urls) {
        List<ExploreUrl> existingUrls = exploreUrlRepository.findAllByUrlIn(urls);
        Set<String> existingUrlSet = existingUrls.stream()
                .map((exploreUrl) -> exploreUrl.url)
                .collect(Collectors.toSet());
        Set<String> filteredUrls = urls.stream()
                .filter(url -> !existingUrlSet.contains(url))
                .collect(Collectors.toSet());
        List<ExploreUrl> urlsToSave = new ArrayList<>();
        for (String filteredUrl : filteredUrls) {
            ExploreUrl exploreUrl = new ExploreUrl();
            exploreUrl.url = filteredUrl;
            exploreUrl.explored = false;
            urlsToSave.add(exploreUrl);
        }
        exploreUrlRepository.saveAll(urlsToSave);
    }

    public void startCrawler(int crawlers) throws Exception {
        Optional<ExploreUrl> next = exploreUrlRepository.findFirstByExploredFalseOrderByIdAsc();
        if (next.isEmpty()) {
            throw new Exception("Não há URLs para explorar. Chame o método configure para iniciar a lista de URLs " +
                    "a serem exploradas");
        }
        controller.addSeed(next.get().url);
        controller.startNonBlocking(factory, crawlers);
    }

    public void stopCrawler() {
        controller.shutdown();
    }
}
