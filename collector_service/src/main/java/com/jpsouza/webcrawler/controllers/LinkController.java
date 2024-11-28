package com.jpsouza.webcrawler.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpsouza.webcrawler.dtos.LinkFilterDTO;
import com.jpsouza.webcrawler.services.LinkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/link")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;

    @GetMapping
    public ResponseEntity<?> findAllPageable(
            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "pageSize") int pageSize,
            @RequestParam(required = false, defaultValue = "ASC", name = "direction") Sort.Direction direction,
            @RequestParam(required = false, defaultValue = "id", name = "sort") String sort,
            @RequestParam(required = false, name = "domain") String domain) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sort));
        return new ResponseEntity<>(linkService.findAllPageable(new LinkFilterDTO(domain), pageable), HttpStatus.OK);
    }
}
