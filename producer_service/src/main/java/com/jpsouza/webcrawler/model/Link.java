package com.jpsouza.webcrawler.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity(name = "link")
@RequiredArgsConstructor
@ToString
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String url;
    public boolean verified;
    @Column(name = "is_product_url", columnDefinition = "default false")
    public boolean isProductUrl;
    @ManyToOne
    @JoinColumn(name = "domain_id", nullable = false)
    @JsonManagedReference
    public Domain domain;
    @Column(name = "verified_in")
    public LocalDateTime verifiedIn;
}