package com.smartinventorymanagementsystem.adrian.controllers;

import com.smartinventorymanagementsystem.adrian.dtos.ProductImageDTO;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sims/api/v1")
@PreAuthorize("hasRole(ADMIN)")
public class ProductImageController {

    private ProductImageService productImageService;

    @Autowired
    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping("/images/product/{productId}")
    public ResponseEntity<List<ProductImageDTO>> getAllProductImagesByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(productImageService.getAllProductImagesByProductId(productId));
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<ProductImageDTO> getProductImageById(@PathVariable Long id) {
        return ResponseEntity.ok(productImageService.getProductImageById(id));
    }

    @PostMapping("/images/{productId}")
    public ResponseEntity<ProductImageDTO> newProductImage(@PathVariable Long productId, @RequestBody ProductImageDTO productImageDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productImageService.saveProductImage(productId, productImageDTO));
    }

    @DeleteMapping("/images/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id){
        productImageService.deleteProductImage(id);
        return ResponseEntity.noContent().build();
    }
}
