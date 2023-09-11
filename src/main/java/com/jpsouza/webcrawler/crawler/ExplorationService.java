package com.jpsouza.webcrawler.crawler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExplorationService {
    private final JSoupCrawlerService jSoupService;

    public void startExploration(int crawlers, Set<String> urls) throws Exception {
        jSoupService.startCrawler(crawlers, urls);
    }

    public void stopExploration() {
        jSoupService.stopCrawler();
    }
}
