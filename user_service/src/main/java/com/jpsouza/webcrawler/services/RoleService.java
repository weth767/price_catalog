package com.jpsouza.webcrawler.services;

import com.jpsouza.webcrawler.models.Role;
import com.jpsouza.webcrawler.repositories.RoleRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> getMemberRole() {
        return roleRepository.findById(3L);
    }
}
