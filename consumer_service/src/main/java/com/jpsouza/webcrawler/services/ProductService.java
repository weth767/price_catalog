package com.jpsouza.webcrawler.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jpsouza.webcrawler.mappers.ResponseProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jpsouza.webcrawler.dtos.ProductDTO;
import com.jpsouza.webcrawler.dtos.ResponseProductDTO;
import com.jpsouza.webcrawler.enums.ProductStatus;
import com.jpsouza.webcrawler.feign.ClassifierFeignClient;
import com.jpsouza.webcrawler.models.FeignClientProduct;
import com.jpsouza.webcrawler.models.Product;
import com.jpsouza.webcrawler.models.ProductPrice;
import com.jpsouza.webcrawler.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ResponseProductMapper responseProductMapper;
    private final ProductRepository productRepository;
    @Autowired
    private final ClassifierFeignClient classifierFeignClient;

    public void getNewProduct(ProductDTO product) {
        // verificar se o produto existe na base, caso não exista perguntar ao usuário
        // se ele quer adicionar
        // se existir, atualizar o preço, a data e o local(site)
        // se não existir, criar na base de ontologia e nesse base de registro, e anexar
        // o primeiro preço.
        FeignClientProduct feignClientProduct = classifierFeignClient.startAnalysis(product);
        if (feignClientProduct != null) {
            saveProduct(feignClientProduct, product);
        }
        System.out.println(feignClientProduct);
        // validações de dados, que façam sentido
        // verificações antologicas(na base de antologia do outro microserviço)
        // verifica se existe, se sim atualiza apenas o preço e a data
        // senão cria o produto e insere o primeiro preço
        // saveProduct(product);
    }

    private void saveProduct(FeignClientProduct feignClientProduct, ProductDTO product) {
        // verificar se o produto já existe na base de dados de, se existir, só atualiza
        // os preços
        Long id = Long.parseLong(Integer.toString(feignClientProduct.getCode()));
        Optional<Product> productOptional = productRepository.findById(id);

        ProductPrice productPrice = new ProductPrice();
        productPrice.setPrice(new BigDecimal(product.getPrice()));
        productPrice.setUrl(product.getUrl());
        productPrice.setDateTime(LocalDateTime.now());
        productPrice.setImageUrl(product.getImageUrl());

        if (productOptional.isPresent()) {
            Product newProduct = productOptional.get();
            List<ProductPrice> productPrices = newProduct.getProductPrices();
            productPrice.setProduct(newProduct);
            productPrices.add(productPrice);
            newProduct.setProductPrices(productPrices);
            productRepository.saveAndFlush(newProduct);
            return;
        }

        Product newProduct = new Product();
        newProduct.setId(Long.parseLong(Integer.toString(feignClientProduct.getCode())));
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        productPrice.setProduct(newProduct);
        newProduct.setProductPrices(new ArrayList<>(List.of(productPrice)));
        newProduct.setStatus(ProductStatus.ACTIVE);
        newProduct.setBrand(product.getBrand());
        productRepository.saveAndFlush(newProduct);
    }

    public Page<ResponseProductDTO> getProductsPaged(Pageable pageable) {
        return productRepository.findAll(pageable).map(responseProductMapper::productToResponseProductDTO);
    }

    public ResponseProductDTO getProductById(Long id) {
        return productRepository.findById(id).map(responseProductMapper::productToResponseProductDTO).orElse(null);
    }
}
