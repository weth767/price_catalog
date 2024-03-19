package com.jpsouza.webcrawler.models;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity(name = "link")
@RequiredArgsConstructor
@ToString
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String url;
    public boolean verified;
    @ManyToOne
    @JoinColumn(name = "domain_id", nullable = false)
    public Domain domain;
    @Column(name = "verified_in")
    public LocalDateTime verifiedIn;
}