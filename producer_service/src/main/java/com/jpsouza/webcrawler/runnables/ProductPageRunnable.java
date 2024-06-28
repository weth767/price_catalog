package com.jpsouza.webcrawler.runnables;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.jpsouza.webcrawler.builders.SchemaNextProductDtoBuilder;
import com.jpsouza.webcrawler.builders.SchemaProductDtoBuilder;
import com.jpsouza.webcrawler.builders.TagProductDtoBuilder;
import com.jpsouza.webcrawler.dtos.ProductDTO;
import com.jpsouza.webcrawler.enums.TagDomain;
import com.jpsouza.webcrawler.kafka.KafkaProducer;
import com.jpsouza.webcrawler.repositories.LinkRepository;

public class ProductPageRunnable implements Runnable {
    private final String url;
    private final KafkaProducer kafkaProducer;
    private final LinkRepository linkRepository;

    private static final Logger LOGGER = LogManager.getLogger(ProductPageRunnable.class);

    public ProductPageRunnable(String url, KafkaProducer kafkaProducer, LinkRepository linkRepository) {
        this.url = url;
        this.kafkaProducer = kafkaProducer;
        this.linkRepository = linkRepository;
    }

    private ProductDTO getDataFromSchema(Element element) throws IOException {
        // Removidas as ocorrencias da seguinte sequencia \\", que atrapalhava na
        // conversão do json
        try {
            String json = element.html().replaceAll("\\\\\\\\\"", "");
            Map<?, ?> schema = new GsonBuilder().setLenient().setPrettyPrinting().disableHtmlEscaping().create()
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
        } catch (JsonSyntaxException e) {
            LOGGER.error(e);
            throw new IOException("Erro ao converter JSON", e);
        }
    }

    private ProductDTO getDataFromSchemaNext(Element element) throws IOException {
        String json = element.html().replaceAll("\\\\\\\\\"", "");
        try {
            Map<?, ?> schema = new GsonBuilder().setLenient().setPrettyPrinting().disableHtmlEscaping().create()
                    .fromJson(json, Map.class);
            if (!schema.containsKey("props")) {
                return null;
            }
            Map<?, ?> props = (Map<?, ?>) schema.get("props");
            if (!props.containsKey("initialState")) {
                return null;
            }
            Map<?, ?> initialState = (Map<?, ?>) props.get("initialState");
            if (!initialState.containsKey("Product") || !initialState.containsKey("ProductPrice")) {
                return null;
            }
            Map<?, ?> product = (Map<?, ?>) initialState.get("Product");
            Map<?, ?> productPrice = (Map<?, ?>) initialState.get("ProductPrice");
            if (!product.containsKey("product")) {
                return null;
            }
            SchemaNextProductDtoBuilder builder = new SchemaNextProductDtoBuilder(product, productPrice, url);
            return builder
                    .imageUrl()
                    .name()
                    .description()
                    .brand()
                    .category()
                    .price()
                    .availability()
                    .condition()
                    .currency()
                    .build();
        } catch (JsonSyntaxException e) {
            LOGGER.error(e);
            throw new IOException("Erro ao converter JSON", e);
        }

    }

    private ProductDTO getData(Document document) throws IOException {
        ProductDTO product = null;
        Elements elements = document.select("script[type=application/ld+json]");
        if (!elements.isEmpty()) {
            Element schema = elements.first();
            product = getDataFromSchema(schema);
        }
        // ler script baseado na "after interactive" do nextjs, onde o script é exibido
        // de outra maneira porque o script do schema só é carregado de fato após a
        // hidratação
        if (Objects.nonNull(product)) {
            return product;
        }
        elements = document.select("script[type=application/json]");
        if (!elements.isEmpty()) {
            Element schema = elements.first();
            product = getDataFromSchemaNext(schema);
        }
        return product;
    }

    /**
     * Não será mais utilizada a busca via tags, porque como cada página tem suas
     * próprias tags, não vale a pena validar isso
     * 
     * @param document
     * @return
     * @throws IOException
     */
    @Deprecated(since = "19/04/2024")
    private ProductDTO getDataFromTags(Document document) throws IOException {
        ProductDTO product = null;
        // precisa aprimorar com talvez, alguma IA que valide melhor as tags
        if (this.url.contains("leroymerlin.com")) {
            product = new TagProductDtoBuilder(document, this.url, TagDomain.LEROYMERLIN).name().image().description()
                    .brand().price()
                    .build();
        }
        return product;
    }

    private HtmlUnitDriver getDriver(boolean acceptRedirect) {
        HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.BEST_SUPPORTED, true);
        driver.getWebClient().getOptions().setCssEnabled(false);
        driver.getWebClient().getOptions().setJavaScriptEnabled(false);
        driver.getWebClient().getOptions().setThrowExceptionOnScriptError(false);
        driver.getWebClient().getOptions().setThrowExceptionOnFailingStatusCode(false);
        driver.getWebClient().getOptions().setPrintContentOnFailingStatusCode(false);
        driver.getWebClient().getOptions().setRedirectEnabled(acceptRedirect);
        driver.getWebClient().getOptions().setTimeout(10000);
        driver.getWebClient().getOptions().setDownloadImages(false);
        driver.getWebClient().waitForBackgroundJavaScript(10000);
        return driver;
    }

    @Override
    public void run() {
        try {
            HtmlUnitDriver driver = getDriver(false);
            driver.get(url);
            Document document = Jsoup.parse(driver.getPageSource());
            ProductDTO productDTO = getData(document);
            if (Objects.nonNull(productDTO)) {
                linkRepository.updateProductLink(url);
                String json = new Gson().toJson(productDTO);
                kafkaProducer.sendMessage(json);
                driver.quit();
                return;
            }
            driver = getDriver(true);
            driver.get(url);
            document = Jsoup.parse(driver.getPageSource());
            productDTO = getData(document);
            if (Objects.nonNull(productDTO)) {
                linkRepository.updateProductLink(url);
                String json = new Gson().toJson(productDTO);
                kafkaProducer.sendMessage(json);
            }
            driver.quit();
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
