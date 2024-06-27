package com.jpsouza.webcrawler.mappers;

import com.jpsouza.webcrawler.dtos.DomainWithoutLinksDTO;
import com.jpsouza.webcrawler.models.Domain;
import org.mapstruct.Mapper;

@Mapper
public interface DomainWithoutLinkMapper {
    DomainWithoutLinksDTO toWithoutLinkDTO(Domain domain);
}
