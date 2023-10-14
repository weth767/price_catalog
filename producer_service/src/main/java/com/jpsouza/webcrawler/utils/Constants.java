package com.jpsouza.webcrawler.utils;

import org.springframework.beans.factory.annotation.Value;

public class Constants {
    @Value("${kafka.topics.products}")
    public static final String TOPIC_PRODUCTS = "";
    @Value("${kafka.topics.links}")
    public static final String TOPIC_LINKS = "";
}
