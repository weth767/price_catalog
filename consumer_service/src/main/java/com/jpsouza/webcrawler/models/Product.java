package com.jpsouza.webcrawler.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "product")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Product {
    @Id
    private Long id;
    private String name;
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @JoinColumn(name = "brand_id")
    @ManyToOne
    private Brand brand;
    private boolean status;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ProductPrice> productPrices = new ArrayList<>();
}
