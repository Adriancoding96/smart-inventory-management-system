package com.smartinventorymanagementsystem.adrian.dtos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
public class CategoryDTO{
    private Long id;
    private String name;
    private String description;
}
