package com.jpsouza.webcrawler.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private String name;
    private String description;
    private String brand;
    private String imageUrl;
    private BigDecimal price;
    private String url;

    public static ProductDTO fromString(String data) {
        String[] dataSplitted = data.split("\n");
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(dataSplitted[0]);
        productDTO.setDescription(dataSplitted[1]);
        productDTO.setBrand(dataSplitted[2]);
        productDTO.setImageUrl(dataSplitted[3]);
        productDTO.setPrice(new BigDecimal(dataSplitted[4]));
        productDTO.setUrl(dataSplitted[5]);
        return productDTO;
    }
}
