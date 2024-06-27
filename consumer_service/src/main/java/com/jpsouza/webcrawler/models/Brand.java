package com.jpsouza.webcrawler.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "brand")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_sequence_generator")
    @SequenceGenerator(name = "brand_sequence_generator", sequenceName = "brand_sequence", allocationSize = 1)
    private Long id;
    private String description;
    private Long code;
}
