package com.jpsouza.webcrawler.services;

import java.util.Set;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jpsouza.webcrawler.crawlers.JSoupCrawler;
import com.jpsouza.webcrawler.runnables.JsoupCompleteCrawlingRunnable;
import com.jpsouza.webcrawler.runnables.JsoupCrawlerRunnable;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class ExplorationService {
    private final JSoupCrawler jSoupCrawler;
    Thread jsoupCrawlerThread;

    public void startExploration(int crawlers, Set<String> urls) {
        jsoupCrawlerThread = new Thread(new JsoupCrawlerRunnable(jSoupCrawler, crawlers, urls));
        jsoupCrawlerThread.start();
    }

    // cron 00:00
    @Scheduled(cron = "0 0 0 * * *", zone = "America/Sao_Paulo")
    private void startExplorationCron() {
        jsoupCrawlerThread = new Thread(new JsoupCompleteCrawlingRunnable(jSoupCrawler, 100));
        jsoupCrawlerThread.start();
    }

    public void stopExploration() {
        jsoupCrawlerThread.interrupt();
        jSoupCrawler.stopCrawler();
    }
}
