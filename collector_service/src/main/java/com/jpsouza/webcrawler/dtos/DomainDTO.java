package com.jpsouza.webcrawler.dtos;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class DomainDTO {
    private Long id;
    private String name;
    private String url;
    private boolean verified;
    public LocalDateTime verifiedIn;
    private Set<LinkWithDomainDTO> links = new HashSet<>();
}
