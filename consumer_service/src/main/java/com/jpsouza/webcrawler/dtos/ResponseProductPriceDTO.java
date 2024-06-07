package com.jpsouza.webcrawler.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ResponseProductPriceDTO {
    private Long id;
    private String url;
    private String imageUrl;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
    private BigDecimal price;
}
