package com.example.barofarm_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleTestController {

    @GetMapping("/admin/only")
    public String adminOnly() {
        return "관리자만 접근 가능합니다.";
    }

    @GetMapping("/user/only")
    public String userOnly() {
        return "사용자 또는 관리자 접근 가능합니다.";
    }
}
