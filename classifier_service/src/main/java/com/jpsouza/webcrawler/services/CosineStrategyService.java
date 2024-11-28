package com.jpsouza.webcrawler.services;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jpsouza.webcrawler.models.Product;

import lombok.NonNull;

@Service
public class CosineStrategyService implements OntologyStrategyService {
    @Override
    public Product compareSimilarity(List<Product> products, @NonNull String word) {
        List<Product> filteredProducts = products.stream()
                .peek((product) -> {
                    double similarity = new info.debatty.java.stringsimilarity.Cosine().distance(
                            product.getDescription(),
                            word);
                    product.setScore(similarity);
                }).filter((product) -> product.getScore() < 0.35)
                .sorted(Comparator.comparing(Product::getScore)).toList();
        return filteredProducts.isEmpty() ? null : filteredProducts.get(0);
    }
}
