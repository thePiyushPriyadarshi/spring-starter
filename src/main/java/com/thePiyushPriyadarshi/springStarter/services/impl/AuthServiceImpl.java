package com.thePiyushPriyadarshi.springStarter.services.impl;

import com.thePiyushPriyadarshi.springStarter.dto.LoginRequestDto;
import com.thePiyushPriyadarshi.springStarter.dto.LoginResponseDto;
import com.thePiyushPriyadarshi.springStarter.dto.SignUpDto;
import com.thePiyushPriyadarshi.springStarter.dto.UserDto;
import com.thePiyushPriyadarshi.springStarter.entities.User;
import com.thePiyushPriyadarshi.springStarter.repositories.UserRepository;
import com.thePiyushPriyadarshi.springStarter.security.JwtService;
import com.thePiyushPriyadarshi.springStarter.services.AuthService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public UserDto signUp(SignUpDto signUpDto) {
        User user = modelMapper.map(signUpDto,User.class);
        return modelMapper.map(userRepository.save(user),UserDto.class);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),loginRequestDto.getPassword())
        );

       User user = (User) authentication.getPrincipal();
       return LoginResponseDto.builder()
               .accessToken(jwtService.generateAccessToken(user))
               .refreshToken(jwtService.generateRefreshToken(user))
               .build();
    }

    @Override
    public UserDto getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public LoginResponseDto refresh(String refreshToken) {

        Long userId = jwtService.getUserIdFromRefreshToken(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AuthenticationServiceException("User not found"));
        return LoginResponseDto.builder().refreshToken(refreshToken).accessToken(jwtService.generateAccessToken(user)).build();
    }
}
