package com.jpsouza.webcrawler.controllers;

import com.jpsouza.webcrawler.models.User;
import com.jpsouza.webcrawler.security.SecurityProvider;
import com.jpsouza.webcrawler.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SecurityProvider provider;

    @GetMapping("/by-email/{email}")
    public ResponseEntity<User> findByemail(@PathVariable String email) {
        return new ResponseEntity<>(userService.findByEmail(email).orElse(null), HttpStatus.OK);
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.findByUsername(username).orElse(null), HttpStatus.OK);
    }
}
