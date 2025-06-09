package com.example.barofarm_backend.service;

import com.example.barofarm_backend.dto.request.AddressRequestDto;
import com.example.barofarm_backend.dto.response.AddressResponseDto;
import com.example.barofarm_backend.entity.Address;
import com.example.barofarm_backend.entity.User;
import com.example.barofarm_backend.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public List<AddressResponseDto> getAddresses(User user) {
        return addressRepository.findAllByUser(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public AddressResponseDto addAddress(User user, AddressRequestDto dto) {
        Address address = Address.builder()
                .addressName(dto.getAddressName())
                .recipientName(dto.getRecipientName())
                .recipientPhone(dto.getRecipientPhone())
                .zipCode(dto.getZipCode())
                .streetAddress(dto.getStreetAddress())
                .detailAddress(dto.getDetailAddress())
                .isDefault(dto.getIsDefault())
                .deliveryRequest(dto.getDeliveryRequest())
                .user(user)
                .build();
        return toDto(addressRepository.save(address));
    }

    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    private AddressResponseDto toDto(Address address) {
        return AddressResponseDto.builder()
                .id(address.getId())
                .addressName(address.getAddressName())
                .recipientName(address.getRecipientName())
                .recipientPhone(address.getRecipientPhone())
                .zipCode(address.getZipCode())
                .streetAddress(address.getStreetAddress())
                .detailAddress(address.getDetailAddress())
                .isDefault(address.getIsDefault())
                .deliveryRequest(address.getDeliveryRequest())
                .build();
    }

    // 배송지 수정
    public AddressResponseDto updateAddress(User user, Long addressId, AddressRequestDto dto) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("주소를 찾을 수 없습니다."));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("권한이 없습니다.");
        }

        address.setAddressName(dto.getAddressName());
        address.setRecipientName(dto.getRecipientName());
        address.setRecipientPhone(dto.getRecipientPhone());
        address.setZipCode(dto.getZipCode());
        address.setStreetAddress(dto.getStreetAddress());
        address.setDetailAddress(dto.getDetailAddress());
        address.setDeliveryRequest(dto.getDeliveryRequest());

        return toDto(addressRepository.save(address));
    }

    // 기본 배송지 설정
    public void setDefaultAddress(User user, Long addressId) {
        Address target = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("주소를 찾을 수 없습니다."));

        if (!target.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("권한이 없습니다.");
        }

        List<Address> addresses = addressRepository.findAllByUser(user);
        for (Address a : addresses) {
            a.setIsDefault(false);
        }
        target.setIsDefault(true);

        addressRepository.saveAll(addresses);
    }

}
