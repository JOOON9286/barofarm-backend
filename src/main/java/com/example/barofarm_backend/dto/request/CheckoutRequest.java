package com.example.barofarm_backend.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckoutRequest {
    private String deliveryDate;
    private String deliveryTime;

    private String receiverName;       // 수령인 이름
    private String receiverPhone;      // 수령인 연락처
    private String zipcode;            // 우편번호
    private String address;            // 기본주소
    private String addressDetail;      // 상세주소
    private String deliveryRequest;    // 배달 요청사항
    private String paymentMethod;      // 결제 수단 (예: CARD, BANK_TRANSFER 등)
    private int totalAmount;    //결제 총합

    private List<ProductInfo> products;

    @Getter
    @Setter
    public static class ProductInfo {
        private Long productId;
        private int quantity;
    }

}
