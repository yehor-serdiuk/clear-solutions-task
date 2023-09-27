package com.serdiuk.task.controller;

import com.serdiuk.task.model.dto.UserCreateDTO;
import com.serdiuk.task.model.dto.UserResponseDTO;
import com.serdiuk.task.model.dto.UserUpdateDTO;
import com.serdiuk.task.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO create(@Valid @RequestBody UserCreateDTO userDTO) {
        return userService.create(userDTO);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.update(id, userUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }

    @GetMapping("/search")
    public List<UserResponseDTO> searchByBirthDateRange(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        java.sql.Date sqlFrom = new java.sql.Date(from.getTime());
        java.sql.Date sqlTo = new java.sql.Date(to.getTime());
        return userService.searchByBirthDateRange(sqlFrom, sqlTo);
    }
}
