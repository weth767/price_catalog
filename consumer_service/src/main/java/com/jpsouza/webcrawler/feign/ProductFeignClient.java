package com.jpsouza.webcrawler.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jpsouza.webcrawler.dtos.ProductDTO;
import com.jpsouza.webcrawler.models.FeignClientProduct;

@FeignClient(value = "product", url = "${feign.classifier.url}")
public interface ProductFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "/product")
    FeignClientProduct createInexistentProduct(@RequestBody ProductDTO product);
}
