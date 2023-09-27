package com.serdiuk.task.service;

import com.serdiuk.task.model.dto.UserCreateDTO;
import com.serdiuk.task.model.dto.UserResponseDTO;
import com.serdiuk.task.model.dto.UserUpdateDTO;

import java.sql.Date;
import java.util.List;

public interface UserService {
    UserResponseDTO create(UserCreateDTO userDTO);
    void update(int id, UserUpdateDTO userUpdateDTO);
    void delete(int id);
    List<UserResponseDTO> searchByBirthDateRange(Date from, Date to);
}
