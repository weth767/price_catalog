package com.jpsouza.webcrawler.dtos;

import com.jpsouza.webcrawler.models.Domain;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class LinkDTO {
    private Long id;
    private String url;
    private boolean verified;
    private LocalDateTime verifiedIn;
    private DomainWithoutLinksDTO domain;
}
