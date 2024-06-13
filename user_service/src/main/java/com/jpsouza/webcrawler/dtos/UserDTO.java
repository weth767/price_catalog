package com.jpsouza.webcrawler.dtos;

import com.jpsouza.webcrawler.models.Role;
import java.util.Date;
import java.util.Set;
import lombok.Data;

@Data
public class UserDTO {
    private long id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private Date createdAt;
    private Date updatedAt;
    private Set<Role> roles;
}
