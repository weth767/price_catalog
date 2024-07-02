package com.jpsouza.webcrawler.runnables;

import com.jpsouza.webcrawler.crawlers.JSoupCrawler;
import com.jpsouza.webcrawler.services.QueueService;

public class JsoupCompleteCrawlingRunnable implements Runnable {
    private final JSoupCrawler jSoupCrawler;
    private final int crawlers;

    public JsoupCompleteCrawlingRunnable(JSoupCrawler jSoupCrawler, int crawlers) {
        this.jSoupCrawler = jSoupCrawler;
        this.crawlers = crawlers;
    }

    @Override
    public void run() {
        try {
            this.jSoupCrawler.startCompleteCrawling(this.crawlers);
        } catch (Exception e) {
            QueueService.getInstance().getQueue().clear();
            QueueService.getInstance().setPolledDomain(null);
            throw new RuntimeException(e);
        }
    }
}
