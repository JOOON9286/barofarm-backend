package com.example.barofarm_backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FarmerSignupRequest {

    private String username;
    private String password;
    private String name;
    private String phone;
    private String address;
    private String certificationNumber;
}
