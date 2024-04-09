package com.jpsouza.webcrawler.mappers;

import org.mapstruct.Mapper;

import com.jpsouza.webcrawler.dtos.ResponseProductDTO;
import com.jpsouza.webcrawler.models.Product;

@Mapper(uses = ResponseProductPriceMapper.class)
public interface ResponseProductMapper {
    ResponseProductDTO productToResponseProductDTO(Product product);
}
