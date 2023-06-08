package com.jpsouza.webcrawler.features.crawler.services;
import java.util.Set;

public interface CustomCrawlerService {
    void startCrawler(int crawlersNumber, Set<String> urls);
    void stopCrawler();
}
