package com.jpsouza.webcrawler.features.crawler.controllers;

import com.jpsouza.webcrawler.features.crawler.services.CustomCrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/crawler")
@RequiredArgsConstructor
public class CustomCrawlerController {
    private final CustomCrawlerService customCrawlerService;
    @PostMapping(value = "/start")
    public ResponseEntity<String> startCrawler() {
        try {
            customCrawlerService.startCrawler(20, new ArrayList<>(List.of("https://www.mercadolivre.com.br/")));
            return ResponseEntity.ok("Crawler iniciado com sucesso!");
        } catch (Exception exception) {
            return ResponseEntity.ok("Crawler não foi iniciado com sucesso! Confira o log: " + exception.getMessage());
        }
    }

    @PutMapping(value = "/stop")
    public ResponseEntity<String> stopCrawler() {
        customCrawlerService.stopCrawler();
        return ResponseEntity.ok("Crawler foi finalizado com sucesso!");
    }
}
