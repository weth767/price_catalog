package com.jpsouza.webcrawler.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jpsouza.webcrawler.models.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface OntologyRepository extends MongoRepository<Product, String> {

}
