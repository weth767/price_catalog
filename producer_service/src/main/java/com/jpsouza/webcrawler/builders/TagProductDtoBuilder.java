package com.jpsouza.webcrawler.builders;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.jpsouza.webcrawler.dtos.ProductDTO;
import com.jpsouza.webcrawler.enums.TagDomain;

public class TagProductDtoBuilder {
    private final Document document;
    private final String receivedUrl;
    private final TagDomain domain;
    // cria um map descrevendo como obter os dados de cada
    private final Map<TagDomain, Map<String, String>> attributes = new HashMap<TagDomain, Map<String, String>>() {
        {
            /*
             * put(TagDomain.AMAZON, new HashMap<String, String>() {
             * {
             * put("price",
             * "span.a-price a-text-price a-size-medium apexPriceToPay > span");
             * put("name", "span.a-size-large product-title-word-break");
             * put("brand", "tr.a-spacing-small po-brand > span.a-size-base po-break-word");
             * put("description", "div#productDescription > p > span");
             * }
             * });
             */
            put(TagDomain.LEROYMERLIN, new HashMap<String, String>() {
                {
                    put("price", "div.price-tag-wrapper div div");
                    put("name", "h1.product-title");
                    put("brand", "th:contains(Marca) + td");
                    put("description",
                            "div[data-product-description]");
                    put("image", "img");
                }
            });
        }
    };

    private String name;
    private String description;
    private String brand;
    private String imageUrl;
    private String price;
    private String url;
    private String availability;
    private String currency;
    private String category;
    private String condition;

    public TagProductDtoBuilder(Document document, String receivedUrl, TagDomain domain) {
        this.document = document;
        this.receivedUrl = receivedUrl;
        this.domain = domain;
    }

    public TagProductDtoBuilder name() {
        Element element = document.selectFirst(attributes.get(this.domain).get("name"));
        if (element != null) {
            this.name = element.text();
        } else {
            this.name = "";
        }
        return this;
    }

    public TagProductDtoBuilder description() {
        Element element = document.selectFirst(attributes.get(this.domain).get("description"));
        if (element != null) {
            this.description = element.text();
        } else {
            this.description = "";
        }
        return this;
    }

    public TagProductDtoBuilder brand() {
        Element element = document.selectFirst(attributes.get(this.domain).get("brand"));
        if (element != null) {
            this.brand = element.text();
        } else {
            this.brand = "";
        }
        return this;
    }

    public TagProductDtoBuilder price() {
        Element element = document.selectFirst(attributes.get(this.domain).get("price"));
        if (element != null) {
            this.price = element.text();
        } else {
            this.price = "";
        }
        return this;
    }

    public TagProductDtoBuilder image() {
        Element image = document.selectFirst(attributes.get(this.domain).get("image"));
        if (image != null) {
            this.imageUrl = image.attr("src");
        } else {
            this.imageUrl = "";
        }
        return this;
    }

    public ProductDTO build() {
        ProductDTO product = new ProductDTO();
        product.setAvailability(this.availability);
        product.setDescription(this.description);
        product.setCurrency(this.currency);
        product.setName(this.name);
        product.setUrl(this.receivedUrl);
        product.setBrand(this.brand);
        product.setCondition(this.condition);
        product.setPrice(this.price);
        product.setImageUrl(this.imageUrl);
        product.setCategory(this.category);
        return product;
    }
}
