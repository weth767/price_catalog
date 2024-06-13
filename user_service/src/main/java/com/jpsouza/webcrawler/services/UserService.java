package com.jpsouza.webcrawler.services;

import com.jpsouza.webcrawler.dtos.UserDTO;
import com.jpsouza.webcrawler.mappers.UserMapper;
import com.jpsouza.webcrawler.models.User;
import com.jpsouza.webcrawler.repositories.UserRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public UserDTO findAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication)) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(authentication.getName());
            return userRepository.findByEmail(userDetails.getUsername()).map(userMapper::toDTO).orElse(null);
        }
        return null;
    }
}
