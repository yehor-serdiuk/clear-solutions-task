package com.serdiuk.task.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserResponseDTO {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String address;
    private String phoneNumber;
}
