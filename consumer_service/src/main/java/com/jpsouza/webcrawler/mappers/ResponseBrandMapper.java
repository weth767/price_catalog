package com.jpsouza.webcrawler.mappers;

import com.jpsouza.webcrawler.dtos.ResponseBrandDTO;
import com.jpsouza.webcrawler.models.Brand;
import org.mapstruct.Mapper;

@Mapper()
public interface ResponseBrandMapper {
    ResponseBrandDTO brandToResponseBrandDTO(Brand brand);
}
