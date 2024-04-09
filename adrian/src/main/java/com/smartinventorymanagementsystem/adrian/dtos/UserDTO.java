package com.smartinventorymanagementsystem.adrian.dtos;

import java.util.HashSet;

import java.util.Set;

import lombok.Data;
import lombok.Setter;

@Data
public class UserDTO {

    private long id;

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private Set<Long> roles = new HashSet<>();
    
}
