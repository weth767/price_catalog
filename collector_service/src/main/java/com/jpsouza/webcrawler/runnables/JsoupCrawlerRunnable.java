package com.jpsouza.webcrawler.runnables;

import com.jpsouza.webcrawler.crawlers.JSoupCrawler;

import java.util.Set;

public class JsoupCrawlerRunnable implements Runnable {
    private final JSoupCrawler jSoupCrawler;
    private final int crawlers;
    private final Set<String> urls;

    public JsoupCrawlerRunnable(JSoupCrawler jSoupCrawler, int crawlers, Set<String> urls) {
        this.jSoupCrawler = jSoupCrawler;
        this.crawlers = crawlers;
        this.urls = urls;
    }

    @Override
    public void run() {
        try {
            this.jSoupCrawler.startCrawler(this.crawlers, this.urls);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
