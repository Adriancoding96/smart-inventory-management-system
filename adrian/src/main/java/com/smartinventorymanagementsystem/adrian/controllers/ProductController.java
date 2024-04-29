package com.smartinventorymanagementsystem.adrian.controllers;

import com.smartinventorymanagementsystem.adrian.dtos.ProductDTO;
import com.smartinventorymanagementsystem.adrian.dtos.ProductWithImagesDTO;
import com.smartinventorymanagementsystem.adrian.requests.ChangeStockRequest;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/products/{id}/increase-stock")
    ResponseEntity<ProductDTO> increaseProductStock(@PathVariable Long id, @RequestBody ChangeStockRequest changeRequest) {
        ProductDTO productDTO = productService.increaseStock(id, changeRequest.getAmount());
        return ResponseEntity.ok(productDTO);
    }

    @PatchMapping("/products/{id}/decrease-stock")
    ResponseEntity<ProductDTO> decreaseProductStock(@PathVariable Long id, @RequestBody ChangeStockRequest changeRequest) {
        ProductDTO productDTO = productService.decreaseStock(id, changeRequest.getAmount());
        return ResponseEntity.ok(productDTO);
    }
}
