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
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.jpsouza.webcrawler.kafka.    KafkaProducer;
import com.jpsouza.webcrawler.services.LinkService;
import com.jpsouza.webcrawler.utils.UrlUtils;

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
            /*
             * WebDriverManager.chromedriver().clearDriverCache();
             * WebDriverManager.chromedriver().clearResolutionCache();
             * WebDriverManager.chromedriver().setup();
             */
            /*
             * ChromeOptions options = new ChromeOptions();
             * options.addArguments("--headless");
             * options.addArguments("--window-size=1920,1080"); // Execução em modo headless
             * options.addArguments("--disable-extensions"); // disabling extensions
             * options.addArguments("--disable-gpu"); // applicable to Windows os only
             * options.addArguments("--disable-dev-shm-usage"); // overcome limited resource
             * problems
             * options.addArguments("--no-sandbox"); // Bypass OS security model
             * options.addArguments("--disable-in-process-stack-traces");
             * options.addArguments("--disable-logging");
             * options.addArguments("--log-level=3");
             * options.addArguments("--remote-allow-origins=*");
             */
            HtmlUnitDriver driver = new HtmlUnitDriver();
            driver.setJavascriptEnabled(true);
            driver.getWebClient().getOptions().setCssEnabled(false);
            driver.getWebClient().getOptions().setJavaScriptEnabled(false);
            driver.getWebClient().getOptions().setThrowExceptionOnScriptError(false);
            driver.getWebClient().getOptions().setThrowExceptionOnFailingStatusCode(false);
            driver.getWebClient().getOptions().setPrintContentOnFailingStatusCode(false);
            driver.getWebClient().getOptions().setRedirectEnabled(false);
            driver.getWebClient().getOptions().setTimeout(5000);
            driver.getWebClient().getOptions().setDownloadImages(false);
            String newUrl = url.startsWith("/") && !url.contains(filteredText)
                    ? (filteredText.endsWith("/") ? filteredText.substring(0, filteredText.length() - 1) : filteredText)
                            + url
                    : url;
            // tratativa se a url contem https://
            // Document document = Jsoup.connect(newUrl).get();
            driver.get(newUrl);
            /*
             * WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
             * wait.until((ExpectedCondition<Boolean>) webDriver -> ((JavascriptExecutor)
             * webDriver)
             * .executeScript("return document.readyState").equals("complete"));
             */
            Document document = Jsoup.parse(driver.getPageSource());
            // feito o ajuste para pegar os links que começam com o dominio do site, por ser
            // que altere em outros lugares
            // mas isso impede de buscar links como /login?redirect="dominio.com.br", talvez
            // validar melhor como ser feito
            // Elements links = document.select("a[href~=^.*" + filteredText + ".*]");
            Elements links = document.select("a[href~=^" + filteredText + ".*]");
            Elements internalLinks = document.select("a[href~=^/.*]");
            Set<String> newLinks = links.stream().map((elementLink) -> elementLink.attr("href"))
                    .collect(Collectors.toSet());
            newLinks.addAll(internalLinks.stream().map((elementLink) -> filteredText + elementLink.attr("href"))
                    .collect(Collectors.toSet()));
            driver.quit();
            kafkaProducer.sendMessage(url);
            return newLinks;
        } catch (Exception exception) {
            System.out.println("Erro: " + exception.getMessage());
            LOGGER.error("Erro: " + exception.getMessage());
            return new HashSet<>();
        }
    }
}
