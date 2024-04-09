package com.smartinventorymanagementsystem.adrian.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartinventorymanagementsystem.adrian.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
