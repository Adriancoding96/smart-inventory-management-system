package com.smartinventorymanagementsystem.adrian.services.Implementations;

import com.smartinventorymanagementsystem.adrian.dtos.ProductDTO;
import com.smartinventorymanagementsystem.adrian.dtos.ProductWithImagesDTO;
import com.smartinventorymanagementsystem.adrian.exceptions.InsufficientStockException;
import com.smartinventorymanagementsystem.adrian.mappers.ProductMapper;
import com.smartinventorymanagementsystem.adrian.models.Category;
import com.smartinventorymanagementsystem.adrian.models.Product;
import com.smartinventorymanagementsystem.adrian.repositories.CategoryRepository;
import com.smartinventorymanagementsystem.adrian.repositories.ProductRepository;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            return productMapper.toDTO(product.get());
        } else {
            throw new EntityNotFoundException("Product with id: " + id + " not found");
        }
    }

    @Override
    public ProductWithImagesDTO getProductWithImagesById(Long id) {
        Optional<Product> product = productRepository.findByIdWithImages(id);
        if(product.isPresent()) {
            return productMapper.toFullDTO(product.get());
        } else {
            throw new EntityNotFoundException("Product with id: " + id + " not found");
        }

    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = productMapper.toProduct(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Optional<Product> product = productRepository.findById(productDTO.getId());
        if(product.isPresent()) {
            Product updatedProduct = updateFields(product.get(), productDTO);
            return productMapper.toDTO(productRepository.save(updatedProduct));
        } else {
            throw new EntityNotFoundException("Product with id: " + productDTO.getId() + " not found");
        }

    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO increaseStock(Long id, int amount) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()) {
            throw new EntityNotFoundException("Product with id: " + id + " not found");
        }
        Product product = optionalProduct.get();
        product.setStockQuantity(product.getStockQuantity() + amount);
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Override
    public ProductDTO decreaseStock(Long id, int amount) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()) {
            throw new EntityNotFoundException("Product with id: " + id + " not found");
        }
        Product product = optionalProduct.get();
        if(product.getStockQuantity() - amount < 0) {
            throw new InsufficientStockException();
        }
        product.setStockQuantity(product.getStockQuantity() - amount);
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    private Product updateFields(Product product, ProductDTO productDTO) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        if(!Objects.equals(product.getCategory().getId(), productDTO.getCategoryId())) {
            Optional<Category> newCategory = categoryRepository.findById(productDTO.getCategoryId());
            if(newCategory.isPresent()) {
                product.setCategory(newCategory.get());
            } else {
                throw new EntityNotFoundException("Category with id: " + productDTO.getCategoryId() + " not found");
            }
        }
        return product;
    }
}
