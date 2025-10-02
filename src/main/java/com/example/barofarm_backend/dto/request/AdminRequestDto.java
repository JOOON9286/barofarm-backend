package com.example.barofarm_backend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminRequestDto {
    private String username;
    private String password;
    private String email;
    private String role;
}