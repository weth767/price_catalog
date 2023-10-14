package com.jpsouza.webcrawler.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "product_link")
@Data
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String link;
    @Column(name = "datetime")
    private LocalDateTime dateTime;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;
}
