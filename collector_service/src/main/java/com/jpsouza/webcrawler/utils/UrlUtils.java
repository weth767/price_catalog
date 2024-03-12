package com.jpsouza.webcrawler.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class UrlUtils {

    public static boolean isUrlValid(String url) {
        try {
            new URI(url).toURL();
            return true;
        } catch (URISyntaxException | MalformedURLException e) {
            return false;
        }
    }

    public static String getOnlyDomainFromUrl(String url) {
        try {
            String newUrl = "https://" + new URI(url).getHost();
            return newUrl;
        } catch (URISyntaxException e) {
            return url;
        }
    }

    public static String getDomainName(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            String newUrl = domain.startsWith("www.") ? domain.substring(4).split("\\.")[0] : domain.split("\\.")[0];
            return newUrl;
        } catch (Exception e) {
            try {
                String newUrl = url.startsWith("www.") ? url.substring(4).split("\\.")[0] : url.split("\\.")[0];
                newUrl = !newUrl.contains("https://") ? "https://" + newUrl : newUrl;
                return newUrl;
            } catch (Exception e1) {
                String newUrl = !url.contains("https://") ? "https://" + url : url;
                return newUrl;
            }
        }
    }
}
