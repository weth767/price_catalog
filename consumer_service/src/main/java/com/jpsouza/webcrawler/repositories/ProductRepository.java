package com.jpsouza.webcrawler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpsouza.webcrawler.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
