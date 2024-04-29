package com.smartinventorymanagementsystem.adrian.controllers;

import com.smartinventorymanagementsystem.adrian.dtos.ProductDTO;
import com.smartinventorymanagementsystem.adrian.dtos.ProductWithImagesDTO;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Todo create endpoints to decrease and increase stock quantity

@RestController
@RequestMapping("/sims/api/v1")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/products/category/{id}")
    ResponseEntity<List<ProductDTO>> getAllProductsByCategoryId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getAllProductsByCategoryId(id));
    }

    @GetMapping("/products/{id}")
    ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/products/full/{id}")
    ResponseEntity<ProductWithImagesDTO> getProductWithImagesById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductWithImagesById(id));
    }

    @PostMapping("/products")
    ResponseEntity<ProductDTO> newProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productDTO));
    }

    @PutMapping("/products")
    ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(productDTO));
    }

    @DeleteMapping("/products/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}
