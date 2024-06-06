package com.jpsouza.webcrawler.repositories;

import com.jpsouza.webcrawler.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
