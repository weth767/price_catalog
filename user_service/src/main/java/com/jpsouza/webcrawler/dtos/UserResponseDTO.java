package com.jpsouza.webcrawler.dtos;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String token;
    private LocalDateTime expiresIn;
    private Set<ResponseRoleDTO> roles;
}
