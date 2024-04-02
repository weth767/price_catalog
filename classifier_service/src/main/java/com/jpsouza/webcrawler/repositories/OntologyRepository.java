package com.jpsouza.webcrawler.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jpsouza.webcrawler.models.Product;

public interface OntologyRepository extends MongoRepository<Product, String> {

}
