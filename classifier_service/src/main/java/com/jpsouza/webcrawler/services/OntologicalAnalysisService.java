package com.jpsouza.webcrawler.services;

import com.jpsouza.webcrawler.dtos.PossibleProductDTO;
import com.jpsouza.webcrawler.repositories.RealProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OntologicalAnalysisService {
    private final RealProductRepository realProductRepository;
    public void startOntologicalAnalysis(PossibleProductDTO product) {
        List<String> descriptionTokens = new ArrayList<>();
        List<String> nameTokens = new ArrayList<>();
        if (product.getDescription() != null) {
            descriptionTokens = extractTokens(product.getDescription());
        }
        if (product.getName() != null) {
            nameTokens = extractTokens(product.getName());
        }
        System.out.println(descriptionTokens);
        System.out.println(nameTokens);
        System.out.println(product.getBrand());
    }

    private List<String> extractTokens(String text) {
        String[] words = text.split("\\s+");
        return Arrays.asList(words);
    }
}
