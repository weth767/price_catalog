package com.jpsouza.webcrawler.models;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "brand")
@ToString
public class Brand {
    @Id
    public Long id;
    public Long code;
    public String description;
    @ToString.Exclude
    @OneToMany(mappedBy = "brand")
    public List<RealProduct> products = new ArrayList<>();
}
