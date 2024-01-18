package com.jpsouza.webcrawler.callables;

import com.jpsouza.webcrawler.kafka.KafkaProducer;
import com.jpsouza.webcrawler.services.LinkService;
import com.jpsouza.webcrawler.utils.UrlUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class JSoupCrawlerCallable implements Callable<Set<String>> {
    private static final Logger LOGGER = LogManager.getLogger(JSoupCrawlerCallable.class);
    private final String url;
    private final String filteredText;
    private final LinkService linkService;
    private final KafkaProducer kafkaProducer;

    public JSoupCrawlerCallable(String url, String filteredText, LinkService linkService, KafkaProducer kafkaProducer) {
        this.url = url;
        this.filteredText = filteredText;
        this.linkService = linkService;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public Set<String> call() {
        /*
         * if (linkService.existsByUrlAndVerifiedTrue(url)) {
         * return new HashSet<>();
         * }
         */
        try {
            // tratativa para os sites onde os links de produtos, não contém a URL base do
            // mesmo
            String newUrl = url.startsWith("/") && !url.contains(filteredText)
                    ? (filteredText.endsWith("/") ? filteredText.substring(0, filteredText.length() - 1) : filteredText)
                            + url
                    : url;
            Document document = Jsoup.connect(newUrl).get();
            Elements links = document.select("a[href~=^.*" + filteredText + ".*]");
            kafkaProducer.sendMessage(url);
            return links.stream().map((elementLink) -> elementLink.attr("href")).filter(UrlUtils::isUrlValid)
                    .collect(Collectors.toSet());
        } catch (MalformedURLException malformedURLException) {
            System.out.println("URL mal formada, precisa ter https ou http: " + url);
            LOGGER.error("URL mal formada, precisa ter https ou http: " + url);
            return new HashSet<>();
        } catch (HttpStatusException httpStatusException) {
            System.out.println("Página não encontrada");
            LOGGER.error("Página não encontrada: " + url);
            return new HashSet<>();
        } catch (UnsupportedMimeTypeException unsupportedMimeTypeException) {
            System.out.println("Tipo de dado não suportado");
            LOGGER.error("Tipo de dado não suportado { " + unsupportedMimeTypeException.getMimeType() + " }: " + url);
            return new HashSet<>();
        } catch (Exception exception) {
            System.out.println("Erro: " + exception.getMessage());
            LOGGER.error("Erro: " + exception.getMessage());
            return new HashSet<>();
        }
    }
}
