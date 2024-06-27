package com.jpsouza.webcrawler.repositories;

import com.jpsouza.webcrawler.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    @Modifying
    @Query(value = "update link set is_product_url = true where url like url", nativeQuery = true)
    @Transactional
    int updateProductLink(@NonNull String url);
}
