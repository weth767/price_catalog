package com.jpsouza.webcrawler.features.exploration.services;

import com.jpsouza.webcrawler.features.crawler.services.Crawler4jCrawlerService;
import com.jpsouza.webcrawler.shared.exceptions.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExplorationService {
    private final Crawler4jCrawlerService crawler4jCrawlerService;

    public void startExploration(int crawlers, Set<String> urls) throws ApplicationException {
        try {
            crawler4jCrawlerService.startCrawler(crawlers, urls);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), e.getCause());
        }
    }

    public void stopExploration() {
        this.crawler4jCrawlerService.stopCrawler();
    }
}
