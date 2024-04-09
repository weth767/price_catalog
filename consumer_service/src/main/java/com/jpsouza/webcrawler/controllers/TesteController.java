package com.jpsouza.webcrawler.controllers;

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
    public ResponseEntity<?> verifyProduct(@RequestBody ProductDTO product) {
        productService.getNewProduct(product);
        return new ResponseEntity<String>("Teste", HttpStatus.OK);
    }
}
