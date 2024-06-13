package com.jpsouza.webcrawler.mappers;

import com.jpsouza.webcrawler.dtos.RoleDTO;
import com.jpsouza.webcrawler.models.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    RoleDTO toDTO(Role role);
}
