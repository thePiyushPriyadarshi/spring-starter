package com.thePiyushPriyadarshi.springStarter.services;

import com.thePiyushPriyadarshi.springStarter.dto.LoginRequestDto;
import com.thePiyushPriyadarshi.springStarter.dto.LoginResponseDto;
import com.thePiyushPriyadarshi.springStarter.dto.SignUpDto;
import com.thePiyushPriyadarshi.springStarter.dto.UserDto;

public interface AuthService{

    UserDto signUp(SignUpDto signUpDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    UserDto getCurrentUser();

    LoginResponseDto refresh(String refreshToken);
}
