package com.jpsouza.webcrawler.repositories;

import com.jpsouza.webcrawler.models.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
    boolean existsByUrl(@NonNull String url);

    List<Domain> findByUrlInOrderByIdAsc(@NonNull Collection<String> urls);

    Optional<Domain> findFirstByVerifiedFalseOrderByIdAsc();

}
