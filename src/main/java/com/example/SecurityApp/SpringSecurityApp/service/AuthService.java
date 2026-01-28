package com.example.SecurityApp.SpringSecurityApp.service;

import com.example.SecurityApp.SpringSecurityApp.dto.LoginDTO;
import com.example.SecurityApp.SpringSecurityApp.dto.LoginResponseDTO;
import com.example.SecurityApp.SpringSecurityApp.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );
        UserEntity user = (UserEntity) authentication.getPrincipal();
        String accessToken = jwtService.generateAcceessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new LoginResponseDTO(user.getId().toString(), accessToken, refreshToken);
    }

    public LoginResponseDTO refresh(String refreshToken) {
        Long userID = jwtService.getUserIdFromToken(refreshToken);
        UserEntity user =  userService.getUserById(userID);

        String accessToken = jwtService.generateAcceessToken(user);
        return new LoginResponseDTO(user.getId().toString(), accessToken, refreshToken);
    }
}
