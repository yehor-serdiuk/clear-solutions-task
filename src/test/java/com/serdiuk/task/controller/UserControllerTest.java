package com.serdiuk.task.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serdiuk.task.model.User;
import com.serdiuk.task.model.dto.UserCreateDTO;
import com.serdiuk.task.model.dto.UserResponseDTO;
import com.serdiuk.task.model.dto.UserUpdateDTO;
import com.serdiuk.task.model.mapper.UserCreateMapper;
import com.serdiuk.task.model.mapper.UserResponseMapper;
import com.serdiuk.task.model.mapper.UserUpdateMapper;
import com.serdiuk.task.repository.UserRepository;
import com.serdiuk.task.service.UserService;
import com.serdiuk.task.service.UserServiceImpl;
import com.serdiuk.task.service.exception.DateParameterException;
import com.serdiuk.task.service.exception.UserTooYoungException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;

    @MockBean
    UserService userService;

    User valid;
    UserCreateDTO validDTO;
    UserUpdateDTO validUpdateDTO;
    UserResponseDTO validResponseDTO;

    User invalid;
    UserCreateDTO invalidDTO;

    User underAge;
    UserCreateDTO underAgeDTO;
    UserCreateMapper userMapper = UserCreateMapper.INSTANCE;
    UserUpdateMapper userUpdateMapper = UserUpdateMapper.INSTANCE;
    UserResponseMapper userResponseMapper = UserResponseMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        underAge = User.builder()
                .id(1)
                .email("email@gmail.com")
                .firstName("first name")
                .lastName("last name")
                .address("address")
                .phoneNumber("+3800000000")
                .birthDate(new Date(System.currentTimeMillis()))
                .build();
        underAgeDTO = userMapper.userToUserDTO(underAge);
        invalid = User.builder()
                .id(1)
                .address("address")
                .phoneNumber("+3800000000")
                .build();
        invalidDTO = userMapper.userToUserDTO(invalid);
        valid = User.builder()
                .id(2)
                .email("email@gmail.com")
                .firstName("first name")
                .lastName("last name")
                .address("address")
                .phoneNumber("+3800000000")
                .birthDate(Date.valueOf("2000-03-10"))
                .build();
        validDTO = userMapper.userToUserDTO(valid);
        validUpdateDTO = userUpdateMapper.userToUserUpdateDTO(valid);
        validResponseDTO = userResponseMapper.userToUserResponseDTO(valid);
    }

    @Test
    void create_whenValid_returnUserResponseDTO() throws Exception {
        when(userService.create(validDTO))
                .thenReturn(validResponseDTO);
        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDTO)))
                .andExpect(status().isCreated());
    }
    @Test
    void create_whenInvalid_returnBadRequest() throws Exception {
        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void create_whenUserUnderAge_returnBadRequest() throws Exception {
        when(userService.create(any()))
                .thenThrow(new UserTooYoungException("user too young"));
        mvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(underAgeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchByBirthDateRange_whenToIsLessThanFrom_returnBadRequest() throws Exception {
        when(userService.searchByBirthDateRange(any(), any()))
                .thenThrow(new DateParameterException("date invalid"));
        mvc.perform(get("/user/search")
                        .param("from", "2023-02-02")
                        .param("to", "2022-02-02"))
                .andExpect(status().isBadRequest());
    }
    @Test
    void searchByBirthDateRange_whenInvalidFormat_returnBadRequest() throws Exception {
        mvc.perform(get("/user/search")
                        .param("from", "2023sdsadf-02-02")
                        .param("to", "2022asdf-02-02"))
                .andExpect(status().isBadRequest());
    }
    @Test
    void searchByBirthDateRange_whenValidFormat_returnOkayStatus() throws Exception {
        mvc.perform(get("/user/search")
                        .param("from", "2002-02-02")
                        .param("to", "2023-02-02"))
                .andExpect(status().isOk());
    }
}