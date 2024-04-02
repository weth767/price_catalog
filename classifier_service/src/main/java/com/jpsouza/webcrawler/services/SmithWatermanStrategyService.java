package com.jpsouza.webcrawler.services;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jpsouza.webcrawler.models.Product;

import lombok.NonNull;

@Service
public class SmithWatermanStrategyService implements OntologyStrategyService {

    @Override
    public Product compareSimilarity(List<Product> products, @NonNull String word) {
        List<Product> filteredProducts = products.stream()
                .map((product) -> {
                    double similarity = 0;
                    try {
                        similarity = new org.simmetrics.metrics.SmithWaterman().compare(product.getDescription(),
                                word);
                    } catch (IllegalArgumentException ex) {
                        similarity = 0;
                    }
                    product.setScore(similarity);
                    return product;
                }).filter((product) -> product.getScore() > 0.7)
                .sorted(Comparator.comparing(Product::getScore).reversed()).toList();
        return filteredProducts.isEmpty() ? null : filteredProducts.get(0);
    }
}
