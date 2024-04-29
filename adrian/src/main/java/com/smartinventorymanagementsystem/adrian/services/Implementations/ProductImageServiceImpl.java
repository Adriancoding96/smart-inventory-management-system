package com.smartinventorymanagementsystem.adrian.services.Implementations;

import com.smartinventorymanagementsystem.adrian.dtos.ProductImageDTO;
import com.smartinventorymanagementsystem.adrian.exceptions.InvalidImageUrlException;
import com.smartinventorymanagementsystem.adrian.mappers.ProductImageMapper;
import com.smartinventorymanagementsystem.adrian.models.Product;
import com.smartinventorymanagementsystem.adrian.models.ProductImage;
import com.smartinventorymanagementsystem.adrian.repositories.ProductImageRepository;
import com.smartinventorymanagementsystem.adrian.repositories.ProductRepository;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.ProductImageService;
import com.smartinventorymanagementsystem.adrian.util.ImageUrlValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private ProductImageRepository productImageRepository;
    private ProductRepository productRepository;
    private ProductImageMapper productImageMapper;
    private ImageUrlValidator imageUrlValidator;

    @Autowired
    public ProductImageServiceImpl(ProductImageRepository productImageRepository, ProductRepository productRepository, ProductImageMapper productImageMapper, ImageUrlValidator imageUrlValidator) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
        this.productImageMapper = productImageMapper;
        this.imageUrlValidator = imageUrlValidator;
    }

    @Override
    public List<ProductImageDTO> getAllProductImagesByProductId(Long productId) {
        List<ProductImage> productImages = productImageRepository.findByProductId(productId);
        return productImages.stream()
                .map(productImageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductImageDTO getProductImageById(Long id) {
        Optional<ProductImage> optionalProductImage = productImageRepository.findById(id);
        if(optionalProductImage.isEmpty()) {
            throw new EntityNotFoundException("ProductImage with id: " + id + " not found");
        }
        return productImageMapper.toDTO(optionalProductImage.get());
    }

    @Override
    public ProductImageDTO saveProductImage(Long productId, ProductImageDTO productImageDTO) {
        if(!imageUrlValidator.isValidImageUrl(productImageDTO.getImageURL())) {
            throw new InvalidImageUrlException();
        }
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isEmpty()) {
            throw new EntityNotFoundException("Product with id: " + productId + " not found");
        }
        ProductImage productImage = productImageMapper.toProductImage(productImageDTO);
        productImage.setProduct(optionalProduct.get());
        productImageRepository.save(productImage);
        return productImageMapper.toDTO(productImage);
    }

    @Override
    public void deleteProductImage(Long id) {
        productImageRepository.deleteById(id);
    }
}
