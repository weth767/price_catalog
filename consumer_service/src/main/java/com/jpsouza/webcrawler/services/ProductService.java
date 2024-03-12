package com.jpsouza.webcrawler.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jpsouza.webcrawler.dtos.ProductDTO;
import com.jpsouza.webcrawler.enums.ProductStatus;
import com.jpsouza.webcrawler.models.Product;
import com.jpsouza.webcrawler.models.ProductPrice;
import com.jpsouza.webcrawler.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void getNewProduct(String productData) {
        ProductDTO product = ProductDTO.fromString(productData);
        // validações de dados, que façam sentido
        // verificações antologicas(na base de antologia do outro microserviço)
        // verifica se existe, se sim atualiza apenas o preço e a data
        // senão cria o produto e insere o primeiro preço
        saveProduct(product);
    }

    private void saveProduct(ProductDTO productDTO) {
        ProductPrice productPrice = new ProductPrice();
        productPrice.setPrice(productPrice.getPrice());
        productPrice.setUrl(productDTO.getUrl());
        productPrice.setDateTime(LocalDateTime.now());
        productPrice.setImageUrl(productDTO.getImageUrl());

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setProductPrices(new ArrayList<>(List.of(productPrice)));
        product.setStatus(ProductStatus.ACTIVE);
        product.setBrand(product.getBrand());

        productRepository.save(product);
    }
}
