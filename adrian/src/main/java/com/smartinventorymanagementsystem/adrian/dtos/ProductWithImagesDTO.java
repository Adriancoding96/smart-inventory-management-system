package com.smartinventorymanagementsystem.adrian.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductWithImagesDTO extends BaseDTO{
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private Long categoryId;
    private List<ProductImageDTO> images;
}
