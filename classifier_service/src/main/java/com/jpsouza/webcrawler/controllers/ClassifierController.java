package com.jpsouza.webcrawler.controllers;

import com.jpsouza.webcrawler.dtos.PossibleProductDTO;
import com.jpsouza.webcrawler.services.OntologicalAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/classifier")
@RequiredArgsConstructor
public class ClassifierController {
    private final OntologicalAnalysisService ontologicalAnalysisService;
    @PostMapping("")
    public ResponseEntity<?> startAnalysis(@RequestBody PossibleProductDTO possibleProduct) {
        ontologicalAnalysisService.startOntologicalAnalysis(possibleProduct);
        return ResponseEntity.ok("");
    }
}
