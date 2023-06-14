package com.jpsouza.webcrawler.shared.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class FormatUtils {
    public static String getOnlyDomainFromUrl(String url) throws URISyntaxException {
        URI uri = new URI(url);
        return uri.getHost();
    }
}
