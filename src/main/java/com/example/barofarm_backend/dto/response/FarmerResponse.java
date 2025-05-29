package com.example.barofarm_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FarmerResponse {

    private Long id;
    private String username;
    private String name;
    private String phone;
    private String address;
    private String certificationNumber;
    private Boolean isVerified;
}
