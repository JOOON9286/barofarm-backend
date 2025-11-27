// 📁 admin/dto/OrderStatusCountsDto.java
package com.example.barofarm_backend.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderStatusCountsDto {
    private long pending;    // 주문 완료 (결제 완료 등)
    private long shipping;   // 배송 중
    private long completed;  // 배송 완료
}