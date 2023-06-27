package com.jpsouza.webcrawler.features.exploration.services;

import com.jpsouza.webcrawler.features.crawler.services.Crawler4jCrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExplorationService {
    private final Crawler4jCrawlerService crawler4jCrawlerService;

    public void startExploration(int crawlers) throws Exception {
        crawler4jCrawlerService.startCrawler(crawlers);
    }

    public void configure(Set<String> urls) {
        crawler4jCrawlerService.configure(urls);
    }

    public void stopExploration() {
        crawler4jCrawlerService.stopCrawler();
    }
}
