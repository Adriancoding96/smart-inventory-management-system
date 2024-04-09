package com.smartinventorymanagementsystem.adrian.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartinventorymanagementsystem.adrian.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
}
