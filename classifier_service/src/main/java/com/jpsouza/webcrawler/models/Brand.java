package com.jpsouza.webcrawler.models;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(collection = "brands")
public class Brand {
    @Id
    private String id;
    private int code;
    private String description;
    private double score;
}
