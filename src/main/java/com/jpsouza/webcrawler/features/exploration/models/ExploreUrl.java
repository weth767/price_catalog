package com.jpsouza.webcrawler.features.exploration.models;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "explore_url")
@RequiredArgsConstructor
@ToString
public class ExploreUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String url;
    public boolean explored;
}