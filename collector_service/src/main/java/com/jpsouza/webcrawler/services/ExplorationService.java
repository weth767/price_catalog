package com.jpsouza.webcrawler.services;

import java.util.Objects;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jpsouza.webcrawler.crawlers.JSoupCrawler;
import com.jpsouza.webcrawler.dtos.ExplorationDataDTO;
import com.jpsouza.webcrawler.runnables.JsoupCompleteCrawlingRunnable;
import com.jpsouza.webcrawler.runnables.JsoupCrawlerRunnable;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class ExplorationService {
    private final JSoupCrawler jSoupCrawler;
    Thread jsoupCrawlerThread;

    public void startExploration(ExplorationDataDTO exceptionData) throws Exception {
        jsoupCrawlerThread = new Thread(new JsoupCrawlerRunnable(jSoupCrawler, exceptionData.crawlers,
                exceptionData.links, exceptionData.reset));
        jsoupCrawlerThread.start();
    }

    // cron 00:00
    @Scheduled(cron = "0 0 0 * * *", zone = "America/Sao_Paulo")
    private void startExplorationCron() {
        jsoupCrawlerThread = new Thread(new JsoupCompleteCrawlingRunnable(jSoupCrawler, 100));
        jsoupCrawlerThread.start();
    }

    public void stopExploration() {
        if (Objects.nonNull(jsoupCrawlerThread)) {
            jsoupCrawlerThread.interrupt();
        }
        jSoupCrawler.stopCrawler();
    }

    public boolean verifyIsCrawlerRunning() {
        return Objects.nonNull(QueueService.getInstance().getPolledDomain())
                || !QueueService.getInstance().getQueue().isEmpty();
    }
}
