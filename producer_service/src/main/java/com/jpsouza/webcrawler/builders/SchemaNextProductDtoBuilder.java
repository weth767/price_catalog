package com.jpsouza.webcrawler.builders;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jpsouza.webcrawler.dtos.ProductDTO;

public class SchemaNextProductDtoBuilder {
    private final Map<?, ?> product;
    private final Map<?, ?> productPrice;

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

    public SchemaNextProductDtoBuilder(Map<?, ?> product, Map<?, ?> productPrice, String url) {
        this.product = product;
        this.productPrice = productPrice;
        this.url = url;
    }

    public SchemaNextProductDtoBuilder imageUrl() {
        Map<?, ?> sku = (Map<?, ?>) product.get("sku");
        @SuppressWarnings("unchecked")
        List<Map<?, ?>> images = (List<Map<?, ?>>) sku.get("images");
        imageUrl = images.isEmpty() ? ""
                : images.stream().collect(Collectors.toList()).get(0).get("url").toString();
        return this;
    }

    public SchemaNextProductDtoBuilder name() {
        Map<?, ?> productData = (Map<?, ?>) product.get("product");
        this.name = productData.get("name").toString();
        return this;
    }

    public SchemaNextProductDtoBuilder description() {
        Map<?, ?> productData = (Map<?, ?>) product.get("product");
        this.description = productData.get("description").toString();
        return this;
    }

    public SchemaNextProductDtoBuilder brand() {
        Map<?, ?> productData = (Map<?, ?>) product.get("product");
        String brand = (String) ((Map<?, ?>) productData.get("brand")).get("name");
        this.brand = brand;
        return this;
    }

    public SchemaNextProductDtoBuilder category() {
        this.category = "";
        return this;
    }

    public SchemaNextProductDtoBuilder price() {
        Map<?, ?> priceData = (Map<?, ?>) productPrice.get("sellPrice");
        price = (String) priceData.get("priceValue").toString();
        return this;
    }

    public SchemaNextProductDtoBuilder availability() {
        this.availability = "https://schema.org/InStock";
        return this;
    }

    public SchemaNextProductDtoBuilder condition() {
        this.condition = "https://schema.org/NewCondition";
        return this;
    }

    public SchemaNextProductDtoBuilder currency() {
        this.currency = "BRL";
        return this;
    }

    public ProductDTO build() {
        ProductDTO product = new ProductDTO();
        product.setAvailability(this.availability);
        product.setDescription(this.description);
        product.setCurrency(this.currency);
        product.setName(this.name);
        product.setUrl(this.url);
        product.setBrand(this.brand);
        product.setCondition(this.condition);
        product.setPrice(this.price);
        product.setImageUrl(this.imageUrl);
        product.setCategory(this.category);
        return product;
    }
}