package com.jpsouza.webcrawler.controllers;

import com.jpsouza.webcrawler.dtos.LoginFormDTO;
import com.jpsouza.webcrawler.dtos.RegisterUserFormDTO;
import com.jpsouza.webcrawler.dtos.UserResponseDTO;
import com.jpsouza.webcrawler.models.User;
import com.jpsouza.webcrawler.security.JwtService;
import com.jpsouza.webcrawler.services.AuthenticationService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @GetMapping("/teste")
    public ResponseEntity<?> teste() {
        return ResponseEntity.ok("teste");
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody RegisterUserFormDTO registerUserForm) {
        return new ResponseEntity<>(authenticationService.signup(registerUserForm), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginFormDTO loginForm) {
        User authenticatedUser = authenticationService.login(loginForm);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setToken(jwtToken);
        userResponse.setExpiresIn(LocalDateTime.now().plus(jwtService.getExpirationTime(), ChronoUnit.MILLIS));
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
