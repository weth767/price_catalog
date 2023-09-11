package com.jpsouza.webcrawler.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class FormatUtils {
    public static String getOnlyDomainFromUrl(String url) throws URISyntaxException {
        URI uri = new URI(url);
        return uri.getHost();
    }

    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}
