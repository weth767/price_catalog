package com.jpsouza.webcrawler.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PossibleProductDTO {
    private String name;
    private String description;
    private String brand;
    private String imageUrl;
    private BigDecimal price;
    private String url;
    private String availability;
    private String currency;
    private String category;
    private String condition;
}
