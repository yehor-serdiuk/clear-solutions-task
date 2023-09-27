package com.serdiuk.task.model.mapper;

import com.serdiuk.task.model.User;
import com.serdiuk.task.model.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserResponseMapper {
    UserResponseMapper INSTANCE = Mappers.getMapper(UserResponseMapper.class);

    UserResponseDTO userToUserResponseDTO(User user);
    List<UserResponseDTO> userListToUserUpdateDTOList(List<User> userList);
}
