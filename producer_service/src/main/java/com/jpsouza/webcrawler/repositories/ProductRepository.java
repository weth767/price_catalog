package com.jpsouza.webcrawler.repositories;

import com.jpsouza.webcrawler.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{}
