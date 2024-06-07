package com.jpsouza.webcrawler.controllers;

import com.jpsouza.webcrawler.dtos.RegisterUserFormDTO;
import com.jpsouza.webcrawler.models.User;
import com.jpsouza.webcrawler.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<User> signUp(@RequestBody RegisterUserFormDTO registerUserForm) {
        return new ResponseEntity<>(authenticationService.signup(registerUserForm), HttpStatus.CREATED);
    }
}
