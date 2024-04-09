package com.jpsouza.webcrawler.dtos;

import java.util.ArrayList;
import java.util.List;

import com.jpsouza.webcrawler.enums.ProductStatus;

import lombok.Data;

@Data
public class ResponseProductDTO {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private ProductStatus status;
    private List<ResponseProductPriceDTO> productPrices = new ArrayList<>();
}