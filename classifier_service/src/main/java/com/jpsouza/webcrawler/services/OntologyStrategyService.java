package com.jpsouza.webcrawler.services;

import java.util.List;

import com.jpsouza.webcrawler.models.Product;

import lombok.NonNull;

public interface OntologyStrategyService {
    public Product compareSimilarity(List<Product> products, @NonNull String word);
}
