package com.serdiuk.task.model.mapper;

import com.serdiuk.task.model.User;
import com.serdiuk.task.model.dto.UserCreateDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserCreateMapper {
    UserCreateMapper INSTANCE = Mappers.getMapper(UserCreateMapper.class);
    User userDTOToUser(UserCreateDTO userDTO);
    UserCreateDTO userToUserDTO(User user);
    List<UserCreateDTO> userListToUserDTOList(List<User> userList);
}
