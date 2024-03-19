package com.jpsouza.webcrawler.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpsouza.webcrawler.dtos.PossibleProductDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/classifier")
@RequiredArgsConstructor
public class ClassifierController {
    @PostMapping("")
    public ResponseEntity<?> startAnalysis(@RequestBody PossibleProductDTO possibleProduct) {
        return ResponseEntity.ok("");
    }
}
