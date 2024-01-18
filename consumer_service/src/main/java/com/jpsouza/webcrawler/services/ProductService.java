package com.jpsouza.webcrawler.consumer_service.services;


import com.jpsouza.webcrawler.consumer_service.dtos.ProductDTO;
import com.jpsouza.webcrawler.consumer_service.enums.ProductStatus;
import com.jpsouza.webcrawler.consumer_service.models.Product;
import com.jpsouza.webcrawler.consumer_service.models.ProductPrice;
import com.jpsouza.webcrawler.consumer_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void getNewProduct(String productData) {
        ProductDTO product =  ProductDTO.fromString(productData)
        //validações de dados, que façam sentido
        //verificações antologicas(na base de antologia do outro microserviço)
        //verifica se existe, se sim atualiza apenas o preço e a data
        //senão cria o produto e insere o primeiro preço
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
