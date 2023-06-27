package com.jpsouza.webcrawler.features.exploration.repositories;

import com.jpsouza.webcrawler.features.exploration.models.ExploreUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ExploreUrlRepository extends JpaRepository<ExploreUrl, Long> {
    List<ExploreUrl> findAllByUrlIn(Set<String> urls);

    Optional<ExploreUrl> findFirstByExploredFalseOrderByIdAsc();
}
