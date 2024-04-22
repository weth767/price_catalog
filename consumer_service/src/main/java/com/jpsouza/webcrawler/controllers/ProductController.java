package com.jpsouza.webcrawler.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpsouza.webcrawler.dtos.ResponseProductDTO;
import com.jpsouza.webcrawler.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    public ResponseEntity<Page<ResponseProductDTO>> getProductsPaged(
            // paginação no spring boot começa sempre pelo 0
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "ASC") Direction direction,
            @RequestParam(required = false, defaultValue = "id") String sort) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sort));
        return new ResponseEntity<>(productService.getProductsPaged(pageable), HttpStatus.OK);
    }

    @GetMapping("/:id")
    public ResponseEntity<ResponseProductDTO> getProductById(
            @PathVariable("id") Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }
}
