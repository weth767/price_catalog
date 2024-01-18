package com.jpsouza.webcrawler.runnables;

import com.google.gson.GsonBuilder;
import com.jpsouza.webcrawler.dtos.ProductDTO;
import com.jpsouza.webcrawler.kafka.KafkaProducer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class ProductPageRunnable implements Runnable{
    private final String url;
    private final KafkaProducer kafkaProducer;

    public ProductPageRunnable(String url, KafkaProducer kafkaProducer) {
        this.url = url;
        this.kafkaProducer = kafkaProducer;
    }

    private String getBrandData(Object data) {
        if (data instanceof String) {
            return (String) data;
        }
        if (data instanceof Map && ((Map<?, ?>) data).containsKey("name")) {
            return (String) ((Map<?, ?>) data).get("name");
        }
        return "";
    }

    private ProductDTO getDataFromSchema(Document document) throws IOException{
        Element element = document.select("script[type=application/ld+json]").first();
        if (Objects.isNull(element)) {
            return null;
        }
        //Removidas as ocorrencias da seguinte sequencia \\", que atrapalhava na conversão do json
        String json = element.html().replaceAll("\\\\\\\\\"", "");
        Map data = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
                .fromJson(json, Map.class);
        if (data.containsKey("@type") && data.get("@type").toString().equalsIgnoreCase("product")) {
            Map offersData = (Map)data.get("offers");
            ProductDTO productDTO = new ProductDTO();
            //validar a forma que a marca aparece
            productDTO.setName(data.get("name").toString().trim());
            productDTO.setDescription(data.get("description").toString().trim());
            productDTO.setPrice(offersData.get("price").toString().trim());
            productDTO.setImageUrl(data.get("image").toString().trim());
            productDTO.setBrand(data.containsKey("brand") ? getBrandData(data.get("brand")) : "");
            productDTO.setUrl(url);
            return productDTO;
        }
        return null;
    }

    private ProductDTO getDataFromTags(Document document) throws  IOException {
        document.
    }

    @Override
    public void run() {
        try {
            Document document = Jsoup.connect(url).get();
            ProductDTO productDTO;
            productDTO = getDataFromSchema(document);
            if (productDTO == null) {
                return;
            }
            kafkaProducer.sendMessage(productDTO.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
