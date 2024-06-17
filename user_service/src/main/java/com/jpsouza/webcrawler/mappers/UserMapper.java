package com.jpsouza.webcrawler.mappers;

import com.jpsouza.webcrawler.dtos.UserDTO;
import com.jpsouza.webcrawler.models.User;
import org.mapstruct.Mapper;

@Mapper(uses = RoleMapper.class)
public interface UserMapper {
    UserDTO toDTO(User user);
}
