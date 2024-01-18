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

    @Override
    public String toString() {
        return
                name + "\n" +
                description + "\n" +
                brand + "\n" +
                imageUrl + "\n" +
                price + "\n" +
                url;
    }
}
