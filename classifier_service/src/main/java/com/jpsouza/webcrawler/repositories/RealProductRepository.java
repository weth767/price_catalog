package com.jpsouza.webcrawler.repositories;

import com.jpsouza.webcrawler.models.RealProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealProductRepository extends JpaRepository<RealProduct, Long> {
    List<RealProduct> findByDescriptionInIgnoreCase(@NonNull List<String> descriptions);
}
