package com.jpsouza.webcrawler.features.exploration.controllers;

import com.jpsouza.webcrawler.features.exploration.services.ExplorationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(value = "/crawler")
@RequiredArgsConstructor
public class ExplorationController {
    private final ExplorationService explorationService;

    @PostMapping(value = "/start")
    public ResponseEntity<String> startCrawler() {
        try {
            explorationService.startExploration(5, new HashSet<>(List.of("https://www.mercadolivre.com.br/")));
            return ResponseEntity.ok("Crawler iniciado com sucesso!");
        } catch (Exception exception) {
            return ResponseEntity.ok("Crawler não foi iniciado com sucesso! Confira o log: " + exception.getMessage());
        }
    }

    @PutMapping(value = "/stop")
    public ResponseEntity<String> stopCrawler() {
        explorationService.stopExploration();
        return ResponseEntity.ok("Crawler foi finalizado com sucesso!");
    }
}
