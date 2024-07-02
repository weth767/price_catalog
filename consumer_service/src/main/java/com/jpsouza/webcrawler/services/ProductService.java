package com.jpsouza.webcrawler.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpsouza.webcrawler.dtos.ProductDTO;
import com.jpsouza.webcrawler.dtos.ResponseProductDTO;
import com.jpsouza.webcrawler.feign.ClassifierFeignClient;
import com.jpsouza.webcrawler.feign.ProductFeignClient;
import com.jpsouza.webcrawler.mappers.ResponseProductMapper;
import com.jpsouza.webcrawler.models.Brand;
import com.jpsouza.webcrawler.models.FeignClientBrand;
import com.jpsouza.webcrawler.models.FeignClientProduct;
import com.jpsouza.webcrawler.models.Product;
import com.jpsouza.webcrawler.models.ProductPrice;
import com.jpsouza.webcrawler.repositories.BrandRepository;
import com.jpsouza.webcrawler.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ResponseProductMapper responseProductMapper;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    @Autowired
    private final ClassifierFeignClient classifierFeignClient;
    @Autowired
    private final ProductFeignClient productFeignClient;

    @Transactional
    public void getProduct(ProductDTO product) {
        FeignClientProduct feignClientProduct = classifierFeignClient.startAnalysis(product);
        if (Objects.isNull(feignClientProduct)) {
            feignClientProduct = productFeignClient.createInexistentProduct(product);
        }
        saveProduct(feignClientProduct, product);
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
        Long lastCode = productRepository.getLastCode() + 1;
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setImageUrl(feignClientProduct.getImageUrl());
        productPrice.setProduct(newProduct);
        newProduct.setProductPrices(new ArrayList<>(List.of(productPrice)));
        newProduct.setStatus(true);
        newProduct.setCode(lastCode);
        // ver algo para resolver a questão de quando não existe a marca, porque tem a
        // questão do code
        Brand brand = generateBrand(feignClientProduct.getBrand());
        newProduct.setBrand(brand);
        productRepository.saveAndFlush(newProduct);
    }

    private Brand generateBrand(FeignClientBrand feignClientBrand) {
        Optional<Brand> optionalBrand = brandRepository.findById(Long.parseLong(feignClientBrand.getCode().toString()));
        if (optionalBrand.isPresent()) {
            return optionalBrand.get();
        }
        Brand brand = new Brand();
        Long lastCode = brandRepository.getLastCode() + 1;
        brand.setCode(lastCode);
        brand.setDescription(feignClientBrand.getDescription());
        return brandRepository.save(brand);
    }

    public Page<ResponseProductDTO> getProductsPaged(String description, Pageable pageable) {
        if (Objects.nonNull(description) && !description.isEmpty()) {
            return productRepository.findByDescriptionLikeIgnoreCaseOrBrand_DescriptionLikeIgnoreCaseOrderByIdAsc(description, description, pageable)
                    .map(responseProductMapper::productToResponseProductDTO);
        }
        return productRepository.findAll(pageable).map(responseProductMapper::productToResponseProductDTO);
    }

    public ResponseProductDTO getProductById(Long id) {
        return productRepository.findById(id).map(responseProductMapper::productToResponseProductDTO).orElse(null);
    }
}
