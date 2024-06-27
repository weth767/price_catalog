package com.jpsouza.webcrawler.controllers;

import com.jpsouza.webcrawler.dtos.DomainFilterDTO;
import com.jpsouza.webcrawler.services.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/domain")
@RequiredArgsConstructor
public class DomainController {
    private final DomainService domainService;

    @GetMapping
    public ResponseEntity<?> findAllPageable(@RequestParam(required = false, defaultValue = "0", name = "page") int page,
                                              @RequestParam(required = false, defaultValue = "10", name = "pageSize") int pageSize,
                                              @RequestParam(required = false, defaultValue = "ASC", name = "direction") Sort.Direction direction,
                                              @RequestParam(required = false, defaultValue = "id", name = "sort") String sort) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sort));
        return new ResponseEntity<>(domainService.findAllPageable(new DomainFilterDTO(), pageable), HttpStatus.OK);
    }
}
