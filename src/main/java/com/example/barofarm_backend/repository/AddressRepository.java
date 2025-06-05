package com.example.barofarm_backend.repository;

import com.example.barofarm_backend.entity.Address;
import com.example.barofarm_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByUser(User user);
}
