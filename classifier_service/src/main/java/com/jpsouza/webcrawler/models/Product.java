package com.jpsouza.webcrawler.models;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "products")
@Data
public class Product {
    @Id
    private String id;
    private int code;
    private BigDecimal price;
    private String imageUrl;
    private Brand brand;
    private String description;
    private double score;
}
