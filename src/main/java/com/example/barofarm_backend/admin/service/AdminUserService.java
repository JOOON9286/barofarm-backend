// 📁 admin/service/AdminUserService.java
package com.example.barofarm_backend.admin.service;

import com.example.barofarm_backend.admin.dto.AdminUserDto;
import com.example.barofarm_backend.entity.User;
import com.example.barofarm_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<AdminUserDto> getAllUsers() {
        // 'UserRepository'에 추가한 'findByRole' 쿼리 사용
        return userRepository.findByRole("ROLE_USER").stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(Long id) {
        // 실제 삭제 로직 (Hard delete)
        userRepository.deleteById(id);
    }

    private AdminUserDto convertToUserDto(User user) {
        return new AdminUserDto(
                user.getId(),
                user.getName(), // 'name' 필드 사용
                user.getEmail(),
                user.getRole()
        );
    }
}