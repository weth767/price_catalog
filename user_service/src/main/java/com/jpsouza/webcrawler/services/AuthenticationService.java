package com.jpsouza.webcrawler.services;

import com.jpsouza.webcrawler.dtos.LoginFormDTO;
import com.jpsouza.webcrawler.dtos.RegisterUserFormDTO;
import com.jpsouza.webcrawler.models.Role;
import com.jpsouza.webcrawler.models.User;
import com.jpsouza.webcrawler.repositories.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;

    public User signup(RegisterUserFormDTO registerUser) {
        User user = new User();
        user.setName(registerUser.getName());
        user.setEmail(registerUser.getEmail());
        user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        user.setPhone(registerUser.getPhone());
        user.setEnabled(true);
        user.setUsername(registerUser.getUsername());
        Optional<Role> optionalRole = roleService.getMemberRole();
        optionalRole.ifPresent(role -> user.setRoles(new HashSet<>(List.of(role))));
        return userRepository.save(user);
    }

    public User login(LoginFormDTO loginForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginForm.getEmail(),
                        loginForm.getPassword()
                )
        );
        System.out.println(authentication);
        return userRepository.findByEmail(loginForm.getEmail())
                .orElseThrow();
    }
}
