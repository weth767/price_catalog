package com.jpsouza.webcrawler.security;

import com.jpsouza.webcrawler.dtos.UserDTO;
import com.jpsouza.webcrawler.mappers.UserMapper;
import com.jpsouza.webcrawler.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class SecurityProvider {
    private final UserService userService;
    @Autowired
    private final UserMapper userMapper;

    @Bean
    UserDetailsService userDetailsService() {
        return username -> userService.findByEmail(username)
                .or(() -> userService.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public UserDTO findAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication)) {
            UserDetails userDetails = userDetailsService().loadUserByUsername(authentication.getName());
            return userService.findByEmail(userDetails.getUsername()).map(userMapper::toDTO).orElse(null);
        }
        return null;
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}