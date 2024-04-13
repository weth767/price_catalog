package com.jpsouza.webcrawler.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import com.jpsouza.webcrawler.dtos.PossibleProductDTO;
import com.jpsouza.webcrawler.models.Brand;
import com.jpsouza.webcrawler.models.BrandProduct;
import com.jpsouza.webcrawler.models.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private final MongoTemplate mongoTemplate;

    public Product createProductInexistent(PossibleProductDTO possibleProduct) throws Exception {
        try {
            if (Objects.isNull(possibleProduct.getBrand()) && Objects.isNull(possibleProduct.getDescription())) {
                throw new Exception("Dados incompletos para adicionar o produto");
            }
            Brand brand = null;
            if (Objects.nonNull(possibleProduct.getBrand())) {
                Query query = new TextQuery(possibleProduct.getBrand()).includeScore().sortByScore();
                List<Brand> brands = mongoTemplate.find(query, Brand.class);
                brand = brands.isEmpty() ? null : brands.get(0);
            }
            if (Objects.isNull(brand) && Objects.nonNull(possibleProduct.getBrand())) {
                Brand newBrand = new Brand();
                newBrand.setCode(getNextBrandCode());
                newBrand.setDescription(possibleProduct.getBrand());
                brand = mongoTemplate.save(newBrand);
            }
            if (Objects.isNull(possibleProduct.getDescription())) {
                throw new Exception("Dados incompletos para adicionar o produto");
            }
            Product product = new Product();
            if (Objects.nonNull(brand)) {
                BrandProduct brandProduct = new BrandProduct();
                brandProduct.setDescription(brand.getDescription());
                brandProduct.setCode(brand.getCode());
                brandProduct.setId(brand.getCode());
                product.setBrand(brandProduct);
            }
            product.setCode(getNextProductCode());
            product.setDescription(possibleProduct.getDescription());
            product.setImageUrl(possibleProduct.getImageUrl());
            product.setPrice(possibleProduct.getPrice());
            return mongoTemplate.save(product);
        } catch (Exception e) {
            throw new Exception("Erro ao salvar o produto");
        }
    }

    @SuppressWarnings("null")
    private int getNextProductCode() throws Exception {
        try {
            Query query = new Query();
            query.with(Sort.by(Sort.Direction.DESC, "code"));
            query.limit(1);
            Product lastProduct = mongoTemplate.findOne(query, Product.class);
            if (Objects.isNull(lastProduct)) {
                return 1;
            }
            return lastProduct.getCode() + 1;
        } catch (Exception e1) {
            return 1;
        }
    }

    @SuppressWarnings("null")
    private int getNextBrandCode() throws Exception {
        try {
            Query query = new Query();
            query.with(Sort.by(Sort.Direction.DESC, "code"));
            query.limit(1);
            Brand lastBrand = mongoTemplate.findOne(query, Brand.class);
            if (Objects.isNull(lastBrand)) {
                return 1;
            }
            return lastBrand.getCode() + 1;
        } catch (Exception e1) {
            return 1;
        }
    }
}
