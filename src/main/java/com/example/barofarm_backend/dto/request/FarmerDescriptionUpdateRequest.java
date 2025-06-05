package com.example.barofarm_backend.dto.request;

import lombok.Getter;
import lombok.Setter;


// 농부 마이페이지 정보변경 dto
@Getter
@Setter
public class FarmerDescriptionUpdateRequest {
    private String description;
}