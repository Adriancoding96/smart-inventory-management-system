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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    @Cacheable(value = "ProductCache", unless = "#result == null")
    public List<ProductDTO> getAllProducts() {
        logger.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        logger.debug("Fetched {} products", products.size());
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProductsByCategoryId(Long categoryId) {
        logger.info("Fetching all products by category id: {}", categoryId);
        List<Product> products = productRepository.findByCategoryId(categoryId);
        logger.debug("Fetched {} products for category id: {}", products.size(), categoryId);
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "ProductCache", unless = "#result == null")
    public ProductDTO getProductById(Long id) {
        logger.info("Fetching product by id: {}", id);
        return productMapper.toDTO(fetchProductById(id));
    }

    @Override
    public ProductWithImagesDTO getProductWithImagesById(Long id) {
        logger.info("Fetching product with images by id: {}", id);
        Product product = fetchProductWithImagesById(id);
        logger.debug("Product with images and id: {} found", id);
        return productMapper.toFullDTO(product);
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        logger.info("Saving product: {}", productDTO);
        Product product = productMapper.toProduct(productDTO);
        Product savedProduct = productRepository.save(product);
        logger.debug("Product saved with id: {}", savedProduct.getId());
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = fetchProductById(productDTO.getId());
        Product updatedProduct = updateFields(product, productDTO);
        logger.debug("Product updated with id: {}", updatedProduct.getId());
        return productMapper.toDTO(productRepository.save(updatedProduct));
    }

    @Override
    public void deleteProduct(Long id) {
        logger.info("Deleting product with id: {}", id);
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            logger.debug("Product with id: {} deleted", id);
        } else {
            logger.warn("Product with id: {} not found for deletion", id);
            throw new EntityNotFoundException("Product with id: " + id + " not found");
        }
    }


    @Override
    public ProductDTO increaseStock(Long id, int amount) {
        return adjustStock(id, amount);
    }

    @Override
    public ProductDTO decreaseStock(Long id, int amount) {
        return adjustStock(id, -amount);
    }

    private ProductDTO adjustStock(Long id, int amount) {
        logger.info("Adjusting stock for product id: {} by amount: {}", id, amount);
        Product product = fetchProductById(id);
        int newStock = product.getStockQuantity() + amount;
        if (newStock < 0) {
            logger.warn("Insufficient stock for product id: {}", id);
            throw new InsufficientStockException();
        }
        product.setStockQuantity(newStock);
        productRepository.save(product);
        logger.debug("Adjusted stock for product id: {} by amount: {}", id, amount);
        return productMapper.toDTO(product);
    }

    private Product fetchProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> {
            logger.warn("Product with id: {} not found", id);
            return new EntityNotFoundException("Product with id: " + id + " not found");
        });
    }
    private Product fetchProductWithImagesById(Long id) {
        return productRepository.findByIdWithImages(id).orElseThrow(() -> {
            logger.warn("Product with images and id: {} not found", id);
            return new EntityNotFoundException("Product with id: " + id + " not found");
        });
    }

    private Product updateFields(Product product, ProductDTO productDTO) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setWeightKG(productDTO.getWeightKG());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        if (!Objects.equals(product.getCategory().getId(), productDTO.getCategoryId())) {
            Optional<Category> newCategory = categoryRepository.findById(productDTO.getCategoryId());
            if (newCategory.isPresent()) {
                product.setCategory(newCategory.get());
            } else {
                throw new EntityNotFoundException("Category with id: " + productDTO.getCategoryId() + " not found");
            }
        }
        return product;
    }
}
