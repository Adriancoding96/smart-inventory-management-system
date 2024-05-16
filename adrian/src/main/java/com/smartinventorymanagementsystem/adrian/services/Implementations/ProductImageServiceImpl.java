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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private static final Logger logger = LoggerFactory.getLogger(ProductImageServiceImpl.class);

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final ProductImageMapper productImageMapper;
    private final ImageUrlValidator imageUrlValidator;

    @Autowired
    public ProductImageServiceImpl(ProductImageRepository productImageRepository, ProductRepository productRepository,
                                   ProductImageMapper productImageMapper, ImageUrlValidator imageUrlValidator) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
        this.productImageMapper = productImageMapper;
        this.imageUrlValidator = imageUrlValidator;
    }

    @Override
    public List<ProductImageDTO> getAllProductImagesByProductId(Long productId) {
        logger.info("Fetching all product images by product id: {}", productId);
        List<ProductImage> productImages = productImageRepository.findByProductId(productId);
        logger.debug("Fetched {} product images for product id: {}", productImages.size(), productId);
        return productImages.stream()
                .map(productImageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductImageDTO getProductImageById(Long id) {
        logger.info("Fetching product image by id: {}", id);
        ProductImage productImage = fetchProductImageById(id);
        return productImageMapper.toDTO(productImage);
    }

    @Override
    public ProductImageDTO saveProductImage(Long productId, ProductImageDTO productImageDTO) {
        logger.info("Saving product image for product id: {}", productId);
        validateImageUrl(productImageDTO.getImageURL());
        Product product = fetchProductById(productId);
        ProductImage productImage = productImageMapper.toProductImage(productImageDTO);
        productImage.setProduct(product);
        ProductImage savedProductImage = productImageRepository.save(productImage);
        logger.debug("Product image saved with id: {}", savedProductImage.getId());
        return productImageMapper.toDTO(savedProductImage);
    }

    @Override
    public void deleteProductImage(Long id) {
        logger.info("Deleting product image with id: {}", id);
        if (productImageRepository.existsById(id)) {
            productImageRepository.deleteById(id);
            logger.debug("Product image with id: {} deleted", id);
        } else {
            logger.warn("Product image with id: {} not found for deletion", id);
            throw new EntityNotFoundException("ProductImage with id: " + id + " not found");
        }
    }

    private ProductImage fetchProductImageById(Long id) {
        return productImageRepository.findById(id).orElseThrow(() -> {
            logger.warn("Product image with id: {} not found", id);
            return new EntityNotFoundException("ProductImage with id: " + id + " not found");
        });
    }

    private Product fetchProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> {
            logger.warn("Product with id: {} not found", productId);
            return new EntityNotFoundException("Product with id: " + productId + " not found");
        });
    }

    private void validateImageUrl(String imageUrl) {
        if (!imageUrlValidator.isValidImageUrl(imageUrl)) {
            logger.warn("Invalid image URL: {}", imageUrl);
            throw new InvalidImageUrlException();
        }
    }
}
