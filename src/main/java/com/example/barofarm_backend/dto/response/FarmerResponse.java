package com.example.barofarm_backend.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FarmerResponse {

    private Long id;
    private String username;
    private String name;
    private String phone;
    private String address;
    private String certificationNumber;
    private Boolean isVerified;
}
