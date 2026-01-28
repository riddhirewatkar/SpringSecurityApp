package com.example.SecurityApp.SpringSecurityApp.advice;

import com.example.SecurityApp.SpringSecurityApp.exception.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.security.core.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> handleResourceNotFoundException(ResourceNotFoundException e) {
//        APIError apiError = APIError
//                .builder()
//                .message(e.getMessage())
//                .status(HttpStatus.NOT_FOUND)
//                .build();
        APIError apiError = new  APIError(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
        return new  ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<APIError> handleAuthenticationException(AuthenticationException e) {
        APIError apiError = new  APIError(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        return new  ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<APIError> handleAuthenticationException(JwtException e) {
        APIError apiError = new  APIError(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        return new  ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }
}
