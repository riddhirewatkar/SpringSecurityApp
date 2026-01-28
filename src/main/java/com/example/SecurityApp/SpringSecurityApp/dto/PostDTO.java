package com.example.SecurityApp.SpringSecurityApp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostDTO {
    private Long id;
    private String title;
    private String content;
}
