package com.jpsouza.webcrawler.services;

import com.jpsouza.webcrawler.mappers.UserMapper;
import com.jpsouza.webcrawler.models.User;
import com.jpsouza.webcrawler.repositories.UserRepository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;;
    @Autowired
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
}
