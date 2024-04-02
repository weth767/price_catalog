package com.jpsouza.webcrawler.services;

import java.util.Comparator;
import java.util.List;

import com.jpsouza.webcrawler.models.Product;

import lombok.NonNull;

public class MongoScoreStrategyService implements OntologyStrategyService {
    @Override
    public Product compareSimilarity(List<Product> products, @NonNull String word) {
        List<Product> filteredProducts = products.stream().filter((product) -> product.getScore() > 1.4)
                .sorted(Comparator.comparing(Product::getScore).reversed()).toList();
        return filteredProducts.isEmpty() ? null : filteredProducts.get(0);
    }
}
