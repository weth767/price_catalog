package com.jpsouza.webcrawler.mappers;

import com.jpsouza.webcrawler.dtos.LinkDTO;
import com.jpsouza.webcrawler.models.Link;
import org.mapstruct.Mapper;

@Mapper(uses = DomainWithoutLinkMapper.class)
public interface LinkMapper {
    LinkDTO toDTO(Link link);
}
