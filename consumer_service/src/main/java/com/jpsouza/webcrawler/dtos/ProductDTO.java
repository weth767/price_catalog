package com.jpsouza.webcrawler.dtos;

import lombok.Data;

@Data
public class ProductDTO {
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
}
