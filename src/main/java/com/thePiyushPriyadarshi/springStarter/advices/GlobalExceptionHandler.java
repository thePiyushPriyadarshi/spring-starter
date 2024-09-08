package com.thePiyushPriyadarshi.springStarter.advices;

import com.thePiyushPriyadarshi.springStarter.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourseNotFound(ResourceNotFoundException e){
        ApiError apiError = ApiError.builder()
                .error(e.getMessage())
                .status(HttpStatus.NOT_FOUND).build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e){
        ApiError apiError = ApiError.builder()
                .error(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException e){
        ApiError apiError = ApiError.builder()
                .error(e.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .build();

        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> handleJwtException(JwtException e){
        ApiError apiError = ApiError.builder()
                .error(e.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .build();

        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleInputValidationErrors(MethodArgumentNotValidException e){
        List<String> errors = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ApiError apiError = ApiError.builder()
                .error("Input validation failed")
                .subErrors(errors)
                .status(HttpStatus.BAD_REQUEST).build();

        return  buildErrorResponseEntity(apiError);
    }



    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError){
        return new ResponseEntity<>(new ApiResponse<>(apiError),apiError.getStatus());
    }
}
