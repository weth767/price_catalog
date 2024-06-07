package com.jpsouza.webcrawler.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ResponseProductDTO {
    private Long id;
    private String name;
    private String description;
    private ResponseBrandDTO brand;
    private boolean status;
    private List<ResponseProductPriceDTO> productPrices = new ArrayList<>();
}