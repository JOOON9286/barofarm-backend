package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.dto.request.PasswordChangeRequest;
import com.example.barofarm_backend.dto.response.UserResponseDto;
import com.example.barofarm_backend.entity.User;
import com.example.barofarm_backend.service.UserService;
import com.example.barofarm_backend.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // 전체 사용자 조회 (관리자용 등)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // ID로 사용자 조회
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody PasswordChangeRequest request) {

        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtTokenProvider.getUserId(token); // JWT에서 이메일 추출

            Optional<User> userOpt = userService.getUserByEmail(email);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(404).body("사용자 정보를 찾을 수 없습니다.");
            }

            User user = userOpt.get();

            // 현재 비밀번호 일치 확인
            if (!userService.checkPassword(user, request.getCurrentPassword())) {
                return ResponseEntity.badRequest().body("현재 비밀번호가 일치하지 않습니다.");
            }

            // 비밀번호 변경
            userService.updatePassword(user, request.getNewPassword());
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("비밀번호 변경 중 오류 발생: " + e.getMessage());
        }
    }

    // 사용자 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtTokenProvider.getUserId(token);

            Optional<User> userOpt = userService.getUserByEmail(email);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(404).body("사용자 정보를 찾을 수 없습니다.");
            }

            User user = userOpt.get();

            UserResponseDto dto = UserResponseDto.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .phone(user.getPhone())
                    .address(user.getAddress())
                    .createdAt(user.getCreatedAt())
                    .build();

            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("인증 실패: " + e.getMessage());
        }
    }

}
