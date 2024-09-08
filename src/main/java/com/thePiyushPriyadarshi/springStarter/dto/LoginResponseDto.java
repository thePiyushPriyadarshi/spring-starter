package com.thePiyushPriyadarshi.springStarter.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

    private String accessToken;
    private String refreshToken;
}
