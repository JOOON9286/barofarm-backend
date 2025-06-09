package com.example.barofarm_backend.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponseDto {
    private Long id;
    private String addressName;
    private String recipientName;
    private String recipientPhone;
    private String deliveryRequest;    // 수령 요청사항
    private String zipCode;
    private String streetAddress;
    private String detailAddress;
    private Boolean isDefault;
}
