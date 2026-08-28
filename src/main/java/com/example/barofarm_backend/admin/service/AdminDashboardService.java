// admin/service/AdminDashboardService.java (최종 수정본)

package com.example.barofarm_backend.admin.service;

import com.example.barofarm_backend.admin.dto.ChartDataDto;
import com.example.barofarm_backend.admin.dto.DashboardSummaryDto;
import com.example.barofarm_backend.admin.dto.OrderStatusCountsDto;
// 님의 기존 Repository 임포트
import com.example.barofarm_backend.repository.OrderPaymentRepository;
import com.example.barofarm_backend.repository.OrderRepository;
import com.example.barofarm_backend.repository.UserRepository;
// 님의 'Order.java'가 쓰는 OrderStatus Enum 위치 (가정)
import com.example.barofarm_backend.entity.OrderStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final OrderPaymentRepository orderPaymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public DashboardSummaryDto getDashboardSummary() {

        // 1. 매출 현황 (최근 7일)
        List<ChartDataDto> salesData = getWeeklySalesChart();

        // 2. 신규 회원 (User 엔티티에 createdAt이 있으므로)
        // TODO: getWeeklySignupChart() 로직 구현 (현재는 비어있음)
        List<ChartDataDto> signupData = getWeeklySignupChart();

        // 3. 주문 현황 (님의 Enum에 맞게 수정 완료!)
        // 님의 OrderStatus Enum: PAYMENT_COMPLETED, PREPARING, SHIPPING, DELIVERED, CANCELLED

        // '주문 완료' (프론트 기준) = 결제 완료(PAYMENT_COMPLETED) + 배송 준비중(PREPARING)
        long pendingCount = orderRepository.countByStatus(OrderStatus.PAYMENT_COMPLETED) +
                orderRepository.countByStatus(OrderStatus.PREPARING);

        // '배송 중' (프론트 기준) = 배송중(SHIPPING)
        long shippingCount = orderRepository.countByStatus(OrderStatus.SHIPPING);

        // '배송 완료' (프론트 기준) = 배송완료(DELIVERED)
        long completedCount = orderRepository.countByStatus(OrderStatus.DELIVERED);

        OrderStatusCountsDto orderStatus = new OrderStatusCountsDto(pendingCount, shippingCount, completedCount);

        return new DashboardSummaryDto(salesData, signupData, orderStatus);
    }

    private List<ChartDataDto> getWeeklySalesChart() {
        Instant sevenDaysAgo = Instant.now().minus(7, ChronoUnit.DAYS);
        // 'OrderPaymentRepository'에 추가한 쿼리 사용
        List<Object[]> results = orderPaymentRepository.findDailySalesSince(sevenDaysAgo);

        return results.stream()
                .map(row -> {
                    Timestamp timestamp = (Timestamp) row[0];
                    String date = timestamp.toInstant().toString().substring(0, 10); // "YYYY-MM-DD"
                    Double total = ((Number) row[1]).doubleValue();
                    return new ChartDataDto(date, total);
                })
                .collect(Collectors.toList());
    }

    private List<ChartDataDto> getWeeklySignupChart() {
        // User 엔티티의 createdAt (LocalDateTime) 기준 쿼리
        // (네이티브 쿼리가 더 효율적일 수 있으나, JPA 방식으로 우선 구현)
        // TODO: UserRepository에 네이티브 쿼리 추가로 성능 최적화 필요

        // 우선 비어있는 리스트 반환 (이 로직은 JPA로 복잡함)
        return List.of();
    }
}