package com.jpsouza.webcrawler.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Data;

@Data
public class Brand {
    private int id;
    private int code;
    private String description;
    @DBRef
    private List<Product> products;
}
