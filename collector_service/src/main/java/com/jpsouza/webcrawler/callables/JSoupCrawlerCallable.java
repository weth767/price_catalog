package com.jpsouza.webcrawler.callables;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.jpsouza.webcrawler.kafka.KafkaProducer;
import com.jpsouza.webcrawler.services.LinkService;
import com.jpsouza.webcrawler.utils.UrlUtils;

import io.github.bonigarcia.wdm.WebDriverManager;

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
        if (linkService.existsByUrlAndVerifiedTrue(url)) {
            return new HashSet<>();
        }
        try {
            // tratativa para os sites onde os links de produtos, não contém a URL base do
            // mesmo
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(false);
            options.addArguments("start-maximized"); // open Browser in maximized mode
            options.addArguments("disable-infobars"); // disabling infobars
            options.addArguments("--disable-extensions"); // disabling extensions
            options.addArguments("--disable-gpu"); // applicable to Windows os only
            options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            options.addArguments("--no-sandbox"); // Bypass OS security model
            options.addArguments("--disable-in-process-stack-traces");
            options.addArguments("--disable-logging");
            options.addArguments("--log-level=3");
            options.addArguments("--remote-allow-origins=*");
            WebDriver driver = new ChromeDriver(options);
            String newUrl = url.startsWith("/") && !url.contains(filteredText)
                    ? (filteredText.endsWith("/") ? filteredText.substring(0, filteredText.length() - 1) : filteredText)
                            + url
                    : url;
            // tratativa se a url contem https://
            // Document document = Jsoup.connect(newUrl).get();
            Document document = Jsoup.parse(driver.getPageSource());
            driver.get(newUrl);
            Elements links = document.select("a[href~=^.*" + filteredText + ".*]");
            driver.quit();
            Set<String> newLinks = links.stream().map((elementLink) -> elementLink.attr("href"))
                    .filter(UrlUtils::isUrlValid)
                    .collect(Collectors.toSet());
            kafkaProducer.sendMessage(url);
            return newLinks;
        } /*
           * catch (MalformedURLException malformedURLException) {
           * System.out.println("URL mal formada, precisa ter https ou http: " + url);
           * LOGGER.error("URL mal formada, precisa ter https ou http: " + url);
           * return new HashSet<>();
           * } catch (HttpStatusException httpStatusException) {
           * System.out.println("Página não encontrada");
           * LOGGER.error("Página não encontrada: " + url);
           * return new HashSet<>();
           * } catch (UnsupportedMimeTypeException unsupportedMimeTypeException) {
           * System.out.println("Tipo de dado não suportado");
           * LOGGER.error("Tipo de dado não suportado { " +
           * unsupportedMimeTypeException.getMimeType() + " }: " + url);
           * return new HashSet<>();
           * }
           */ catch (Exception exception) {
            System.out.println("Erro: " + exception.getMessage());
            LOGGER.error("Erro: " + exception.getMessage());
            return new HashSet<>();
        }
    }
}
