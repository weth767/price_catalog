package com.jpsouza.webcrawler.models;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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