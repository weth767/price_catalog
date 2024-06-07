package com.jpsouza.webcrawler.controllers;

import com.jpsouza.webcrawler.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpsouza.webcrawler.dtos.ProductDTO;
import com.jpsouza.webcrawler.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/verify")
@RequiredArgsConstructor
public class TesteController {
    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<Product> verifyProduct(@RequestBody ProductDTO product) {
        return new ResponseEntity<>(productService.getProduct(product), HttpStatus.OK);
    }
}
