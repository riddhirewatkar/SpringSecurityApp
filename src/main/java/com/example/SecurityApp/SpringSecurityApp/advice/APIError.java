package com.example.SecurityApp.SpringSecurityApp.advice;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class APIError {
    private LocalDateTime timestamp;
    private String message;
    private HttpStatus status;

    public APIError() {
        this.timestamp = LocalDateTime.now();
    }

    public APIError(String message, HttpStatus status) {
        this();
        this.message = message;
        this.status = status;
    }
}
