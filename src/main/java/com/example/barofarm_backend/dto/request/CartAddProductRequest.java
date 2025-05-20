package com.example.barofarm_backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartAddProductRequest {//상품을 장바구니 추가할때

    private Long userId;
    private Long productId;
    private String productName;
    private int quantity;
    private int price;

}
