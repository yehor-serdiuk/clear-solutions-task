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
public class UserUpdateDTO {
    @Email(message = "Invalid email")
    private String email;
    private String firstName;
    private String lastName;

    @Past(message = "Invalid birth date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

    private String address;
    private String phoneNumber;
}
