package com.example.barofarm_backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {
    private String password;
    private String email;
    private String nickname;
}
