package com.jpsouza.webcrawler.features.exploration.repositories;

import com.jpsouza.webcrawler.features.exploration.models.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
    Optional<Domain> findByUrlLike(@NonNull String url);
}
