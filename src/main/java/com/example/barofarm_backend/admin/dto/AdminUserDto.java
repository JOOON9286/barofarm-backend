// admin/dto/AdminUserDto.java
package com.example.barofarm_backend.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminUserDto {
    private Long id;
    private String username;
    private String email;
    private String role;     // "ROLE_USER"
}