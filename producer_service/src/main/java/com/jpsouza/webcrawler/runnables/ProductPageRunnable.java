package com.jpsouza.webcrawler.runnables;

import com.google.gson.GsonBuilder;
import com.jpsouza.webcrawler.builders.SchemaProductDtoBuilder;
import com.jpsouza.webcrawler.dtos.ProductDTO;
import com.jpsouza.webcrawler.kafka.KafkaProducer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ProductPageRunnable implements Runnable {
    private final String url;
    private final KafkaProducer kafkaProducer;

    public ProductPageRunnable(String url, KafkaProducer kafkaProducer) {
        this.url = url;
        this.kafkaProducer = kafkaProducer;
    }

    private ProductDTO getDataFromSchema(Document document) {
        Element element = document.select("script[type=application/ld+json]").first();
        if (Objects.isNull(element)) {
            return null;
        }
        //Removidas as ocorrencias da seguinte sequencia \\", que atrapalhava na conversão do json
        String json = element.html().replaceAll("\\\\\\\\\"", "");
        Map<?, ?> schema = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
                .fromJson(json, Map.class);
        // verifica se o schema é dividido em vários JSONs
        if (schema.containsKey("@graph")) {
            Optional<?> optional = schema.values().stream().filter((value) ->
                    value instanceof Map<?, ?> &&
                            ((Map<?, ?>) value).containsKey("@type") &&
                            ((Map<?, ?>) value).get("@type").equals("product")).findFirst();
            return optional.map(optionalSchema -> new SchemaProductDtoBuilder((Map<?, ?>) optionalSchema, url)
                    .name()
                    .description()
                    .brand()
                    .category()
                    .offersAssociatedData()
                    .build()).orElse(null);
        }
        if (schema.containsKey("@type") && schema.get("@type").toString().equalsIgnoreCase("product")) {
            return new SchemaProductDtoBuilder(schema, url)
                    .name()
                    .description()
                    .brand()
                    .category()
                    .offersAssociatedData()
                    .build();
        }
        return null;
    }

    private ProductDTO getData(Document document) throws IOException {
        ProductDTO product = getDataFromSchema(document);
        if (Objects.isNull(product)) {
            product = getDataFromTags(document);
        }
        return product;
    }

    private ProductDTO getDataFromTags(Document document) throws IOException {
        return null;
    }

    @Override
    public void run() {
        try {
            Document document = Jsoup.connect(url).get();
            ProductDTO productDTO = getData(document);
            if (Objects.isNull(productDTO)) {
                return;
            }
            kafkaProducer.sendMessage(productDTO.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
