package com.jpsouza.webcrawler.runnables;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jpsouza.webcrawler.builders.SchemaProductDtoBuilder;
import com.jpsouza.webcrawler.builders.TagProductDtoBuilder;
import com.jpsouza.webcrawler.dtos.ProductDTO;
import com.jpsouza.webcrawler.enums.TagDomain;
import com.jpsouza.webcrawler.kafka.KafkaProducer;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ProductPageRunnable implements Runnable {
    private final String url;
    private final KafkaProducer kafkaProducer;

    public ProductPageRunnable(String url, KafkaProducer kafkaProducer) {
        this.url = url;
        this.kafkaProducer = kafkaProducer;
    }

    private ProductDTO getDataFromSchema(Element element) {
        // Removidas as ocorrencias da seguinte sequencia \\", que atrapalhava na
        // conversão do json
        String json = element.html().replaceAll("\\\\\\\\\"", "");
        Map<?, ?> schema = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
                .fromJson(json, Map.class);
        // verifica se o schema é dividido em vários JSONs
        if (schema.containsKey("@graph")) {
            List<?> graph = (List<?>) schema.get("@graph");
            Optional<?> optional = graph.stream()
                    .filter((subschema) -> subschema instanceof Map<?, ?>
                            && ((Map<?, ?>) subschema).containsKey("@type")
                            && ((Map<?, ?>) subschema).get("@type").toString().equalsIgnoreCase("product"))
                    .findFirst();
            return optional.map(optionalSchema -> new SchemaProductDtoBuilder((Map<?, ?>) optionalSchema, url)
                    .name()
                    .imageUrl()
                    .description()
                    .brand()
                    .category()
                    .offersAssociatedData()
                    .build()).orElse(null);
        }
        if (schema.containsKey("@type") && schema.get("@type").toString().equalsIgnoreCase("product")) {
            return new SchemaProductDtoBuilder(schema, url)
                    .name()
                    .imageUrl()
                    .description()
                    .brand()
                    .category()
                    .offersAssociatedData()
                    .build();
        }
        return null;
    }

    private ProductDTO getData(Document document) throws IOException {
        ProductDTO product = null;
        Elements elements = document.select("script[type=application/ld+json]");
        if (!elements.isEmpty()) {
            Element schema = elements.first();
            product = getDataFromSchema(schema);
        }
        if (Objects.isNull(product)) {
            product = getDataFromTags(document);
        }
        return product;
    }

    private ProductDTO getDataFromTags(Document document) throws IOException {
        ProductDTO product = null;
        if (this.url.contains("leroymerlin.com")) {
            product = new TagProductDtoBuilder(document, this.url, TagDomain.LEROYMERLIN).name().image().description()
                    .brand().price()
                    .build();
        }
        return product;
    }

    @Override
    public void run() {
        try {
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
            driver.get(url);
            Document document = Jsoup.parse(driver.getPageSource());
            ProductDTO productDTO = getData(document);
            if (Objects.isNull(productDTO)) {
                return;
            }
            driver.quit();
            String json = new Gson().toJson(productDTO);
            kafkaProducer.sendMessage(json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
