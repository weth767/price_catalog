package com.jpsouza.webcrawler.features.crawler.models;

import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity(name = "product")
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public String title;
    @Column(name = "image_url")
    public String imageUrl;
    public String price;
}
