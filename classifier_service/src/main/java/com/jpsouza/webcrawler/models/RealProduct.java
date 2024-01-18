package com.jpsouza.webcrawler.models;

import lombok.RequiredArgsConstructor;
import lombok.ToString;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "product")
@RequiredArgsConstructor
@ToString
public class RealProduct {
    @Id
    public Long id;
    public Long code;
    public String description;
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    public Brand brand;
    @Column(name = "image_url")
    public String imageUrl;
}
