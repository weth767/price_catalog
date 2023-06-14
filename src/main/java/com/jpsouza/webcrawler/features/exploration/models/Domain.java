package com.jpsouza.webcrawler.features.exploration.models;

import lombok.RequiredArgsConstructor;

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
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public String url;
    public boolean verified;
    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL)
    public Set<Link> links = new HashSet<>();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "url = " + url + ", " +
                "verified = " + verified + ")";
    }
}
