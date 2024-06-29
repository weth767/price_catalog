package com.jpsouza.webcrawler.runnables;

import java.util.Set;

import com.jpsouza.webcrawler.crawlers.JSoupCrawler;

public class JsoupCrawlerRunnable implements Runnable {
    private final JSoupCrawler jSoupCrawler;
    private final int crawlers;
    private final Set<String> urls;
    private final boolean reset;

    public JsoupCrawlerRunnable(JSoupCrawler jSoupCrawler, int crawlers, Set<String> urls, boolean reset) {
        this.jSoupCrawler = jSoupCrawler;
        this.crawlers = crawlers;
        this.urls = urls;
        this.reset = reset;
    }

    @Override
    public void run() {
        try {
            this.jSoupCrawler.startCrawler(this.crawlers, this.urls, this.reset);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
