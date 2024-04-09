package com.jpsouza.webcrawler.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ResponseProductPriceDTO {
    private Long id;
    private String url;
    private String imageUrl;
    private LocalDateTime dateTime;
    private BigDecimal price;
}
