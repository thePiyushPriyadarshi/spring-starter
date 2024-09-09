package com.thePiyushPriyadarshi.springStarter.dto;

import com.thePiyushPriyadarshi.springStarter.entities.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email should be a valid email")
    private String email;

    @Size(min = 8, message = "Minimum length of password should be greater than 8")
    private String password;

    private Gender gender;
}
