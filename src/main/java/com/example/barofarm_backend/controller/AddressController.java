package com.example.barofarm_backend.controller;

import com.example.barofarm_backend.dto.request.AddressRequestDto;
import com.example.barofarm_backend.dto.response.AddressResponseDto;
import com.example.barofarm_backend.entity.User;
import com.example.barofarm_backend.service.AddressService;
import com.example.barofarm_backend.service.UserService;
import com.example.barofarm_backend.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users/me/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    private User getUserFromToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtTokenProvider.getEmail(token);
        return userService.getUserByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    //배송지 조회
    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> getMyAddresses(@RequestHeader("Authorization") String authHeader) {
        User user = getUserFromToken(authHeader);
        return ResponseEntity.ok(addressService.getAddresses(user));
    }
    
    //배송지 등록
    @PostMapping
    public ResponseEntity<AddressResponseDto> addAddress(@RequestHeader("Authorization") String authHeader,
                                                         @RequestBody AddressRequestDto dto) {
        User user = getUserFromToken(authHeader);
        return ResponseEntity.ok(addressService.addAddress(user, dto));
    }
    
    //배송지 수정
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> updateAddress(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long addressId,
            @RequestBody AddressRequestDto dto) {

        User user = getUserFromToken(authHeader);
        return ResponseEntity.ok(addressService.updateAddress(user, addressId, dto));
    }
    
    
    //배송지 삭제
    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }
    
    //기본 배송지 설정
    @PutMapping("/{addressId}/default")
    public ResponseEntity<String> setDefaultAddress(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long addressId) {

        User user = getUserFromToken(authHeader);
        addressService.setDefaultAddress(user, addressId);
        return ResponseEntity.ok("기본 배송지로 설정되었습니다.");
    }



}
