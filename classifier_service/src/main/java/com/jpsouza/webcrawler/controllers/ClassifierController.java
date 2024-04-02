package com.jpsouza.webcrawler.controllers;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpsouza.webcrawler.callables.OntologyCallable;
import com.jpsouza.webcrawler.configuration.MongoConfig;
import com.jpsouza.webcrawler.dtos.PossibleProductDTO;
import com.jpsouza.webcrawler.enums.SimilarityMethod;
import com.jpsouza.webcrawler.models.Product;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/classifier")
@RequiredArgsConstructor
public class ClassifierController {
    @PostMapping("")
    public ResponseEntity<?> startAnalysis(@RequestBody PossibleProductDTO possibleProduct) {
        MongoTemplate template = new MongoConfig().mongoTemplate();
        Callable<Product> callable = new OntologyCallable(possibleProduct, SimilarityMethod.COSINE, template);
        Future<Product> future = Executors.newFixedThreadPool(1).submit(callable);
        try {
            Product product = future.get();
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ExecutionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
