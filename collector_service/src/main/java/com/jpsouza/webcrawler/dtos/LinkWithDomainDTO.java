package com.jpsouza.webcrawler.dtos;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class LinkWithDomainDTO {
    private Long id;
    private String url;
    private boolean verified;
    private LocalDateTime verifiedIn;
}
