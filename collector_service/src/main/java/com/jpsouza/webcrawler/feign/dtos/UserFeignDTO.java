package com.jpsouza.webcrawler.feign.dtos;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserFeignDTO implements UserDetails {
    private long id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String password;
    private boolean enabled;
    private Date createdAt;
    private Date updatedAt;
    private Set<RoleFeignDTO> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
