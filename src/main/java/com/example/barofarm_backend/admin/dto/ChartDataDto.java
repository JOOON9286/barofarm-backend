package com.example.barofarm_backend.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartDataDto {
    private String x; // x축 (e.g., "Mon", "Tue")
    private Double y; // y축 (e.g., 120000.0, 5.0)
}