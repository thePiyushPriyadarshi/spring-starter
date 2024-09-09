package com.thePiyushPriyadarshi.springStarter.controllers;


import com.thePiyushPriyadarshi.springStarter.dto.LoginRequestDto;
import com.thePiyushPriyadarshi.springStarter.dto.LoginResponseDto;
import com.thePiyushPriyadarshi.springStarter.dto.SignUpDto;
import com.thePiyushPriyadarshi.springStarter.dto.UserDto;
import com.thePiyushPriyadarshi.springStarter.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody @Valid SignUpDto signUpDto){
        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        return new ResponseEntity<>(authService.signUp(signUpDto), HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response){
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
        Cookie cookie = new Cookie("refreshToken",loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);

    }
    @GetMapping("/protected")
    public ResponseEntity<UserDto> protectedRoute(){
        return ResponseEntity.ok(authService.getCurrentUser());

    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request){

         String refreshToken = Arrays.stream(request.getCookies())
                 .filter(cookie -> "refreshToken".equals(cookie.getName()))
                 .findFirst().map(Cookie::getValue)
                 .orElseThrow(()-> new AuthenticationServiceException("Refresh token not found"));

        return ResponseEntity.ok(authService.refresh(refreshToken));

    }
}
