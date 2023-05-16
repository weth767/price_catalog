package com.jpsouza.webcrawler.features.crawler.services;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;

@Service
public class CustomCrawlerService extends WebCrawler {
    private static final String crawlStorageFolder = "/tmp/crawler4j/";
    private CrawlController controller;

    public void startCrawler(int numberOfCrawlers, ArrayList<String> urls) throws Exception {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(CustomCrawlerService.crawlStorageFolder);
        File folder = new File(config.getCrawlStorageFolder());
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        controller = new CrawlController(config, pageFetcher, robotstxtServer);
        urls.forEach(controller::addSeed);
        CrawlController.WebCrawlerFactory<CustomCrawlerService> factory = CustomCrawlerService::new;
        controller.startNonBlocking(factory, numberOfCrawlers);
    }

    public void stopCrawler() {
        controller.shutdown();
    }
}
