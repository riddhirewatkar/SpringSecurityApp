package com.example.SecurityApp.SpringSecurityApp.controller;

import com.example.SecurityApp.SpringSecurityApp.dto.LoginDTO;
import com.example.SecurityApp.SpringSecurityApp.dto.LoginResponseDTO;
import com.example.SecurityApp.SpringSecurityApp.dto.SignUpDTO;
import com.example.SecurityApp.SpringSecurityApp.dto.UserDTO;
import com.example.SecurityApp.SpringSecurityApp.exception.ResourceNotFoundException;
import com.example.SecurityApp.SpringSecurityApp.service.AuthService;
import com.example.SecurityApp.SpringSecurityApp.service.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {
    private final UserServiceImpl userService;
    private final AuthService authService;

    @PostMapping(path = "/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        LoginResponseDTO loginResponseDTO = authService.login(loginDTO);

        Cookie cookie = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request)
    {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Invalid refreshToken"));

        LoginResponseDTO loginResponseDTO = authService.refresh(refreshToken);
        return ResponseEntity.ok(loginResponseDTO);
    }
}
