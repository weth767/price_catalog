package com.jpsouza.webcrawler.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import com.jpsouza.webcrawler.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @NonNull
    Page<Product> findAll(@NonNull Pageable pageable);

    @Query(value = "SELECT code FROM product WHERE code = (SELECT MAX(code) FROM product)", nativeQuery = true)
    Long getLastCode();

    Page<Product> findByDescriptionLikeIgnoreCaseOrBrand_DescriptionLikeIgnoreCaseOrderByIdAsc(@NonNull String description, @NonNull String description1, Pageable pageable);
}
