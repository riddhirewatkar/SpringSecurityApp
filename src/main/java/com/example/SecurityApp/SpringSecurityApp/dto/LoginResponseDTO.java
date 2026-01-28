package com.example.SecurityApp.SpringSecurityApp.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private String id;
    private String accessToken;
    private String refreshToken;
}
