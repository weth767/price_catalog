package com.jpsouza.webcrawler.core.repositories;

import com.jpsouza.webcrawler.core.models.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    boolean existsByUrlAndVerifiedTrue(@NonNull String url);

    boolean existsByUrl(@NonNull String url);

    List<Link> findByUrlInOrderByIdAsc(@NonNull Collection<String> urls);

    Optional<Link> findByUrl(@NonNull String url);
}
