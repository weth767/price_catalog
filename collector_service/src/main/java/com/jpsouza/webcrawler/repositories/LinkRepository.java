package com.jpsouza.webcrawler.repositories;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jpsouza.webcrawler.models.Link;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    boolean existsByUrlAndVerifiedTrue(@NonNull String url);

    boolean existsByUrl(@NonNull String url);

    List<Link> findByUrlInOrderByIdAsc(@NonNull Collection<String> urls);

    Optional<Link> findByUrl(@NonNull String url);

    boolean existsByUrlIgnoreCaseAndVerifiedInBefore(@NonNull String url, @Nullable LocalDateTime verifiedIn);

    @Modifying
    @Query(value = "update link set verified = false", nativeQuery = true)
    @Transactional
    void resetAllLinks();

    Page<Link> findByDomain_NameLikeIgnoreCase(@NonNull String name, Pageable pageable);
}
