package com.serdiuk.task.service;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserCreateMapper userCreateMapper = UserCreateMapper.INSTANCE;
    private final UserUpdateMapper userUpdateMapper = UserUpdateMapper.INSTANCE;
    private final UserResponseMapper userResponseMapper = UserResponseMapper.INSTANCE;
    @Override
    public UserResponseDTO create(UserCreateDTO userDTO) {
        LocalDate birthDate = userDTO.getBirthDate().toLocalDate();
        LocalDate now = LocalDate.now();
        if (Period.between(birthDate, now).getYears() < 18) {
            throw new UserTooYoungException("The user is too young");
        }
        User user = userRepository.save(userCreateMapper.userDTOToUser(userDTO));
        return userResponseMapper.userToUserResponseDTO(user);
    }

    @Override
    public void update(int id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userUpdateMapper.updateUserFromUserUpdateDto(userUpdateDTO, user);
        userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponseDTO> searchByBirthDateRange(Date from, Date to) {
        if(from.after(to)) {
            throw new DateParameterException("The 'to' date is less than the 'from' date");
        }
        List<User> userList = userRepository.findAllByBirthDateBetween(from, to);
        return userResponseMapper.userListToUserUpdateDTOList(userList);
    }
}
