package com.jpsouza.webcrawler.dtos;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class DomainWithoutLinksDTO {
    private Long id;
    private String name;
    private String url;
    private boolean verified;
    public LocalDateTime verifiedIn;
}
