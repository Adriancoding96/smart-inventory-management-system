package com.smartinventorymanagementsystem.adrian.services.Interfaces;

import com.smartinventorymanagementsystem.adrian.dtos.ProductImageDTO;

import java.util.List;

public interface ProductImageService {
    List<ProductImageDTO> getAllProductImagesByProductId(Long productId);
    ProductImageDTO getProductImageById(Long id);
    ProductImageDTO saveProductImage(Long productId, ProductImageDTO productImageDTO);
    void deleteProductImage(Long id);
}
