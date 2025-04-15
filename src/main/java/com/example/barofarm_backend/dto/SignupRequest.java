package com.example.barofarm_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {

    private String email;
    private String password;
    private String name;
    private String phone;
}