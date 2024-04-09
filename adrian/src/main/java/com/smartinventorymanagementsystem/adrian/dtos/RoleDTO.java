package com.smartinventorymanagementsystem.adrian.dtos;

import java.util.Set;
import java.util.HashSet;

import lombok.Data;

@Data
public class RoleDTO {
    private Long id;
    private String name;
    private Set<Long> userIds = new HashSet<>();
}
