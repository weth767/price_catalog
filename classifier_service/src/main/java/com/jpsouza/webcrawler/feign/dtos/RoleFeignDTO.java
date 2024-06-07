package com.jpsouza.webcrawler.feign.dtos;

import lombok.Data;

@Data
public class RoleFeignDTO {
    private Long id;
    private String name;
    private boolean enabled;
}
