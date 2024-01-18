package com.jpsouza.webcrawler.dtos;

import lombok.Data;
import java.math.BigDecimal;


@Data
public class PossibleProductDTO {
    private String name;
    private String description;
    private String brand;
    private String imageUrl;
    private BigDecimal price;
    private String url;
}
