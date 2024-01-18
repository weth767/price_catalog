package com.jpsouza.webcrawler.consumer_service.repositories;

import com.jpsouza.webcrawler.consumer_service.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{}
