package com.serdiuk.task.service;

import com.serdiuk.task.config.AppProperties;
import com.serdiuk.task.model.User;
import com.serdiuk.task.model.dto.UserCreateDTO;
import com.serdiuk.task.model.dto.UserResponseDTO;
import com.serdiuk.task.model.dto.UserUpdateDTO;
import com.serdiuk.task.model.mapper.UserCreateMapper;
import com.serdiuk.task.model.mapper.UserResponseMapper;
import com.serdiuk.task.model.mapper.UserUpdateMapper;
import com.serdiuk.task.repository.UserRepository;
import com.serdiuk.task.service.exception.DateParameterException;
import com.serdiuk.task.service.exception.UserNotFoundException;
import com.serdiuk.task.service.exception.UserTooYoungException;
import org.h2.engine.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.control.MappingControl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    User valid;
    UserCreateDTO validDTO;
    UserUpdateDTO validUpdateDTO;
    UserResponseDTO validResponseDTO;
    
    User underAge;
    UserCreateDTO underAgeDTO;
    @Mock
    AppProperties appProperties;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;

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
    void create_whenUnderAgeUser_throwsUserTooYoungException() {
        when(appProperties.getAge())
                .thenReturn(18);
        assertThrows(UserTooYoungException.class,
                () -> userService.create(underAgeDTO));
    }
    @Test
    void create_whenValidUser_DoesNotThrow() {
        assertDoesNotThrow(() -> userService.create(validDTO));
    }
    
    @Test
    void update_whenInvalidId_throwsUserNotFoundException() {
        when(userRepository.findById(1))
                .thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                () -> userService.update(1, validUpdateDTO));
    }
    @Test
    void update_whenValidId_doesNotThrow() {
        when(userRepository.findById(1))
                .thenReturn(Optional.of(valid));
        assertDoesNotThrow(() -> userService.update(1, validUpdateDTO));
    }

    @Test
    void searchByBirthDateRange_whenToIsLessThanFrom_throwDateParameterException() {
        Date from = Date.valueOf("2023-02-02");
        Date to = Date.valueOf("2022-02-02");
        assertThrows(DateParameterException.class,
                () -> userService.searchByBirthDateRange(from, to));
    }
    @Test
    void searchByBirthDateRange_whenValid_doesNotThrow() {
        Date from = Date.valueOf("2022-02-02");
        Date to = Date.valueOf("2023-02-02");
        assertDoesNotThrow(() -> userService.searchByBirthDateRange(from, to));
    }
}