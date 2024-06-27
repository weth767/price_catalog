package com.jpsouza.webcrawler.mappers;

import com.jpsouza.webcrawler.dtos.DomainDTO;
import com.jpsouza.webcrawler.models.Domain;
import org.mapstruct.Mapper;

@Mapper(uses = LinkWithoutDomainMapper.class)
public interface DomainMapper {
    DomainDTO toDTO(Domain domain);
}
