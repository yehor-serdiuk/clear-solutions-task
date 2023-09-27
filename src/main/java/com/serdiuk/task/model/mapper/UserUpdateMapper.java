package com.serdiuk.task.model.mapper;

import com.serdiuk.task.model.User;
import com.serdiuk.task.model.dto.UserUpdateDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserUpdateMapper {
    UserUpdateMapper INSTANCE = Mappers.getMapper(UserUpdateMapper.class);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromUserUpdateDto(UserUpdateDTO dto, @MappingTarget User entity);
    
    UserUpdateDTO userToUserUpdateDTO(User user);
}
