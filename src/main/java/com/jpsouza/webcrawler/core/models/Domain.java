package com.jpsouza.webcrawler.core.models;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    public Set<Link> links = new HashSet<>();
}
