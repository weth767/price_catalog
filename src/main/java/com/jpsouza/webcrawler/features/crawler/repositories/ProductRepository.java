package com.jpsouza.webcrawler.features.crawler.repositories;

import com.jpsouza.webcrawler.features.crawler.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
