package com.jpsouza.webcrawler.mappers;

import org.mapstruct.Mapper;

import com.jpsouza.webcrawler.dtos.ResponseProductPriceDTO;
import com.jpsouza.webcrawler.models.ProductPrice;

@Mapper
public interface ResponseProductPriceMapper {
    ResponseProductPriceDTO productPriceToResponseProductPriceDTO(ProductPrice productPrice);
}
