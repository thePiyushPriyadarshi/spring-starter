package com.thePiyushPriyadarshi.springStarter.dto;

import com.thePiyushPriyadarshi.springStarter.entities.enums.Gender;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private Gender gender;
}
