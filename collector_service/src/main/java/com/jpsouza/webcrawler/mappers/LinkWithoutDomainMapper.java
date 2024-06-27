package com.jpsouza.webcrawler.mappers;

import com.jpsouza.webcrawler.dtos.LinkWithDomainDTO;
import com.jpsouza.webcrawler.models.Link;
import org.mapstruct.Mapper;

@Mapper
public interface LinkWithoutDomainMapper {
    LinkWithDomainDTO toWithDomainDTO(Link link);
}
