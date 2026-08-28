// admin/dto/DashboardSummaryDto.java
package com.example.barofarm_backend.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class DashboardSummaryDto {
    private List<ChartDataDto> salesChart;      // 매출 현황
    private List<ChartDataDto> signupChart;   // 신규 회원
    private OrderStatusCountsDto orderStatus; // 주문 현황
}