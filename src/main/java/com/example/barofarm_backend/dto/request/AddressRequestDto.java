package com.example.barofarm_backend.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequestDto {

    private String addressName; //배송지명
    private String recipientName;      // 수령인 이름
    private String recipientPhone;     // 수령인 연락처
    private String deliveryRequest;    // 수령 요청사항
    private String zipCode;
    private String streetAddress;   // ex 덕릉로777
    private String detailAddress;   // 101-102호
    private Boolean isDefault;         // 기본 배송지 여부
}
