package com.jpsouza.webcrawler.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "domain")
@RequiredArgsConstructor
@ToString
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public String url;
    public boolean verified;
    @ToString.Exclude
    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL)
    @JsonBackReference
    public Set<Link> links = new HashSet<>();
    @Column(name = "verified_in")
    public LocalDateTime verifiedIn;
}
