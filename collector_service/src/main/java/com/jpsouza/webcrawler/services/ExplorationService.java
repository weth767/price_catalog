package com.jpsouza.webcrawler.services;


import com.jpsouza.webcrawler.crawlers.JSoupCrawler;
import com.jpsouza.webcrawler.runnables.JsoupCrawlerRunnable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExplorationService {
    private final JSoupCrawler jSoupCrawler;
    Thread jsoupCrawlerThread;

    public void startExploration(int crawlers, Set<String> urls) {
        jsoupCrawlerThread = new Thread(new JsoupCrawlerRunnable(jSoupCrawler, crawlers, urls));
        jsoupCrawlerThread.start();
    }

    public void stopExploration() {
        jsoupCrawlerThread.interrupt();
        jSoupCrawler.stopCrawler();
    }
}
