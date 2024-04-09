package com.jpsouza.webcrawler.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jpsouza.webcrawler.dtos.ProductDTO;
import com.jpsouza.webcrawler.models.FeignClientProduct;

@FeignClient(value = "classifier", url = "http://localhost:8083")
public interface ClassifierFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "/classifier")
    FeignClientProduct startAnalysis(@RequestBody ProductDTO product);
}
