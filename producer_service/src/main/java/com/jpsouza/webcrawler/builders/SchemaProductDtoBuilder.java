package com.jpsouza.webcrawler.builders;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.jpsouza.webcrawler.dtos.ProductDTO;

public class SchemaProductDtoBuilder {
    private final Map<?, ?> schema;
    private final String receivedUrl;

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

    public SchemaProductDtoBuilder(Map<?, ?> schema, String receivedUrl) {
        this.schema = schema;
        this.receivedUrl = receivedUrl;
    }

    public SchemaProductDtoBuilder imageUrl() {
        if (!this.schema.containsKey("image")) {
            this.imageUrl = "";
            return this;
        }
        Object imageData = this.schema.get("image");
        if (imageData instanceof String) {
            this.imageUrl = (String) imageData;
            return this;
        }
        if (imageData instanceof Map && ((Map<?, ?>) imageData).containsKey("url")) {
            this.imageUrl = (String) ((Map<?, ?>) imageData).get("url");
            return this;
        }
        if (imageData instanceof List<?> && !((List<?>) imageData).isEmpty()) {
            this.imageUrl = (String) ((List<?>) imageData).get(0);
            return this;
        }
        this.imageUrl = "";
        return this;
    }

    public SchemaProductDtoBuilder name() {
        if (!this.schema.containsKey("name")) {
            this.name = "";
        } else {
            this.name = this.schema.get("name").toString().trim();
        }
        return this;
    }

    public SchemaProductDtoBuilder description() {
        if (!this.schema.containsKey("description")) {
            this.description = "";
        } else {
            this.description = this.schema.get("description").toString().trim();
        }
        return this;
    }

    public SchemaProductDtoBuilder brand() {
        if (!this.schema.containsKey("brand")) {
            this.brand = "";
            return this;
        }
        Object brandData = this.schema.get("brand");
        if (brandData instanceof String) {
            this.brand = (String) brandData;
            return this;
        }
        if (brandData instanceof Map && ((Map<?, ?>) brandData).containsKey("name")) {
            this.brand = (String) ((Map<?, ?>) brandData).get("name");
            return this;
        }
        if (brandData instanceof Map && !((Map<?, ?>) brandData).keySet().isEmpty()) {
            Optional<?> foundedKey = ((Map<?, ?>) brandData).keySet().stream().filter((key) -> !key.toString()
                    .equalsIgnoreCase("@type")).findFirst();
            this.brand = Optional.of(foundedKey).map(Optional::toString).orElse("");
            return this;
        }
        this.brand = "";
        return this;
    }

    public SchemaProductDtoBuilder category() {
        this.currency = this.schema.containsKey("category") ? (String) this.schema.get("category") : "";
        return this;
    }

    public SchemaProductDtoBuilder offersAssociatedData() {
        if (!this.schema.containsKey("offers") ||
                (!(this.schema.get("offers") instanceof Map) &&
                        !(this.schema.get("offers") instanceof List<?>))) {
            this.price = "0";
            this.condition = "";
            this.availability = "https://schema.org/OutOfStock";
            this.currency = "BRL";
            this.url = this.receivedUrl;
            return this;
        }
        if (this.schema.get("offers") instanceof List<?> && ((List<?>) this.schema.get("offers")).isEmpty()) {
            this.price = "0";
            this.condition = "";
            this.availability = "https://schema.org/OutOfStock";
            this.currency = "BRL";
            this.url = this.receivedUrl;
            return this;
        }
        if (this.schema.get("offers") instanceof List<?>) {
            Map<?, ?> offersData = (Map<?, ?>) ((List<?>) this.schema.get("offers")).get(0);
            price = offersData.containsKey("price") ? offersData.get("price").toString()
                    : offersData.containsKey("lowPrice") ? offersData.get("lowPrice").toString()
                            : offersData.containsKey("highPrice") ? offersData.get("highPrice").toString() : "0";
            condition = offersData.containsKey("itemCondition") ? (String) offersData.get("itemCondition")
                    : this.schema.containsKey("itemCondition") ? (String) this.schema.get("itemCondition") : "";
            availability = offersData.containsKey("availability") ? (String) offersData.get("availability")
                    : "https://schema.org/OutOfStock";
            currency = offersData.containsKey("priceCurrency") ? (String) offersData.get("priceCurrency") : "BRL";
            url = offersData.containsKey("url") ? (String) offersData.get("url") : this.receivedUrl;
            return this;
        }
        Map<?, ?> offersData = (Map<?, ?>) this.schema.get("offers");
        price = offersData.containsKey("price") ? offersData.get("price").toString()
                : offersData.containsKey("lowPrice") ? offersData.get("lowPrice").toString()
                        : offersData.containsKey("highPrice") ? offersData.get("highPrice").toString() : "0";
        condition = offersData.containsKey("itemCondition") ? (String) offersData.get("itemCondition")
                : this.schema.containsKey("itemCondition") ? (String) this.schema.get("itemCondition") : "";
        availability = offersData.containsKey("availability") ? (String) offersData.get("availability")
                : "https://schema.org/OutOfStock";
        currency = offersData.containsKey("priceCurrency") ? (String) offersData.get("priceCurrency") : "BRL";
        url = offersData.containsKey("url") ? (String) offersData.get("url") : this.receivedUrl;
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
