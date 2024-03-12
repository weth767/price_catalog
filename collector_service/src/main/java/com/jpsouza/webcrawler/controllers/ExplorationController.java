package com.jpsouza.webcrawler.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpsouza.webcrawler.dtos.ExplorationDataDTO;
import com.jpsouza.webcrawler.services.ExplorationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/crawler")
@RequiredArgsConstructor
public class ExplorationController {
    private final ExplorationService explorationService;

    @PostMapping(value = "/start")
    public ResponseEntity<String> startCrawler(@RequestBody ExplorationDataDTO explorationData) {
        try {
            explorationService.startExploration(explorationData.crawlers, explorationData.links);
            return ResponseEntity.ok("Crawler iniciado com sucesso!");
        } catch (Exception exception) {
            return ResponseEntity.internalServerError()
                    .body("Crawler não foi iniciado com sucesso! Confira o log: " + exception.getMessage());
        }
    }

    @PutMapping(value = "/stop")
    public ResponseEntity<String> stopCrawler() {
        explorationService.stopExploration();
        return ResponseEntity.ok("Crawler foi finalizado com sucesso!");
    }
}
