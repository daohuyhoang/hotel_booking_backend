package com.daisy.daisy_hotel_backend.repository;

import com.daisy.daisy_hotel_backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
