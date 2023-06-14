package com.jpsouza.webcrawler.features.crawler.services;

import com.jpsouza.webcrawler.features.exploration.models.Domain;
import com.jpsouza.webcrawler.features.exploration.models.Link;
import com.jpsouza.webcrawler.features.exploration.repositories.DomainRepository;
import com.jpsouza.webcrawler.features.exploration.repositories.LinkRepository;
import com.jpsouza.webcrawler.shared.exceptions.FolderNotAccessibleException;
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
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class Crawler4jCrawlerService extends WebCrawler {
    private static final String crawlStorageFolder = "src/main/resources/crawler4j/";
    private final static Pattern FILTERS = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1");
    private final DomainRepository domainRepository;
    private final LinkRepository linkRepository;
    public CrawlController controller;
    public CrawlConfig config;
    public CrawlController.WebCrawlerFactory<Crawler4jCrawlerService> factory;

    public Crawler4jCrawlerService(DomainRepository domainRepository, LinkRepository linkRepository) throws Exception {
        this.domainRepository = domainRepository;
        this.linkRepository = linkRepository;
        config = new CrawlConfig();
        config.setMaxDownloadSize(5000000);
        config.setCrawlStorageFolder(Crawler4jCrawlerService.crawlStorageFolder);
        File folder = new File(config.getCrawlStorageFolder());
        if (!folder.canWrite() || !folder.canRead()) {
            throw new FolderNotAccessibleException(String.format("Diretório selecionado para o Crawler4J, não permite leitura ou escrita: %s", folder.getAbsolutePath()));
        }
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        factory = () -> new Crawler4jCrawlerService(this.domainRepository, this.linkRepository);
        controller = new CrawlController(config, pageFetcher, robotstxtServer);
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        super.shouldVisit(referringPage, url);
        return !FILTERS.matcher(href).matches();
    }

    @Override
    public void visit(Page page) {
        System.out.println("URL: " + page.getWebURL().getURL());
        try {
            Optional<Domain> domain = domainRepository.findByUrlLike(FormatUtils.getOnlyDomainFromUrl(page.getWebURL().getURL()));
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

                Link link = new Link();
                link.url = page.getWebURL().getURL();
                link.verified = true;
                link.domain = newDomain;
                linkRepository.save(link);
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void startCrawler(int crawlers, Set<String> urls) {
        urls.forEach((url) -> controller.addSeed(url));
        controller.startNonBlocking(factory, crawlers);
    }

    public void stopCrawler() {
        controller.shutdown();
    }
}
