package com.smartinventorymanagementsystem.adrian.controllers;

import com.smartinventorymanagementsystem.adrian.dtos.ProductDTO;
import com.smartinventorymanagementsystem.adrian.dtos.ProductWithImagesDTO;
import com.smartinventorymanagementsystem.adrian.requests.ChangeStockRequest;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/sims/api/v1")
@PreAuthorize("hasRole(ADMIN)")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/products/category/{id}")
    public ResponseEntity<List<ProductDTO>> getAllProductsByCategoryId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getAllProductsByCategoryId(id));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));

    }

    /*@GetMapping("/products/rust/{id}")
    public Mono<ResponseEntity<ProductDTO>> getProductByIdRust(@PathVariable Long id) {
        return productService.getProductByIdRust(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }*/

    @GetMapping("/products/full/{id}")
    public ResponseEntity<ProductWithImagesDTO> getProductWithImagesById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductWithImagesById(id));
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> newProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productDTO));
    }

    @PutMapping("/products")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(productDTO));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/products/{id}/increase-stock")
    public ResponseEntity<ProductDTO> increaseProductStock(@PathVariable Long id, @RequestBody ChangeStockRequest changeRequest) {
        ProductDTO productDTO = productService.increaseStock(id, changeRequest.getAmount());
        return ResponseEntity.ok(productDTO);
    }

    @PatchMapping("/products/{id}/decrease-stock")
    public ResponseEntity<ProductDTO> decreaseProductStock(@PathVariable Long id, @RequestBody ChangeStockRequest changeRequest) {
        ProductDTO productDTO = productService.decreaseStock(id, changeRequest.getAmount());
        return ResponseEntity.ok(productDTO);
    }
}
