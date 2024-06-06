package com.jpsouza.webcrawler.controllers;

import com.jpsouza.webcrawler.dtos.LoginFormDTO;
import com.jpsouza.webcrawler.dtos.ResponseRoleDTO;
import com.jpsouza.webcrawler.dtos.UserResponseDTO;
import com.jpsouza.webcrawler.models.User;
import com.jpsouza.webcrawler.security.JwtService;
import com.jpsouza.webcrawler.services.AuthenticationService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
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
@RequestMapping("/signin")
@RequiredArgsConstructor
public class SignInController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginFormDTO loginForm) {
        User authenticatedUser = authenticationService.login(loginForm);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setToken(jwtToken);
        userResponse.setExpiresIn(LocalDateTime.now().plus(jwtService.getExpirationTime(), ChronoUnit.MILLIS));
        userResponse.setRoles(authenticatedUser.getRoles().stream().map((role) ->
                new ResponseRoleDTO(role.getId(), role.getName())).collect(Collectors.toSet()));
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
