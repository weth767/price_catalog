package com.jpsouza.webcrawler.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpsouza.webcrawler.dtos.PossibleProductDTO;
import com.jpsouza.webcrawler.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<?> createProductInexistent(@RequestBody PossibleProductDTO possibleProduct) {
        try {
            return new ResponseEntity<>(productService.createProductInexistent(possibleProduct), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
