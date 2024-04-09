package com.jpsouza.webcrawler.models;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FeignClientProduct {
    private String id;
    private int code;
    private BigDecimal price;
    private String imageUrl;
    private FeignClientBrand brand;
    private String description;
    private double score;
}
