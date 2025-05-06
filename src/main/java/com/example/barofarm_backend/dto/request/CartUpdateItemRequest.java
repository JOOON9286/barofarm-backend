package com.example.barofarm_backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartUpdateItemRequest {//상품 수량 수정

    private Long cartItemId;
    private int quantity;
}
