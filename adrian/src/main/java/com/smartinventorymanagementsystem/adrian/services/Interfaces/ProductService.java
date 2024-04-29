package com.smartinventorymanagementsystem.adrian.services.Interfaces;


import com.smartinventorymanagementsystem.adrian.dtos.ProductDTO;
import com.smartinventorymanagementsystem.adrian.dtos.ProductWithImagesDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getAllProductsByCategoryId(Long categoryId);
    ProductDTO getProductById(Long id);
    ProductWithImagesDTO getProductWithImagesById(Long id);
    ProductDTO saveProduct(ProductDTO productDTO);
    ProductDTO updateProduct(ProductDTO productDTO);
    void deleteProduct(Long id);
    ProductDTO increaseStock(Long id, int amount);
    ProductDTO decreaseStock(Long id, int amount);
}
