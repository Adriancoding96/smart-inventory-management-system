package com.smartinventorymanagementsystem.adrian.services.Implementations;

import com.smartinventorymanagementsystem.adrian.dtos.CategoryDTO;
import com.smartinventorymanagementsystem.adrian.mappers.CategoryMapper;
import com.smartinventorymanagementsystem.adrian.models.Category;
import com.smartinventorymanagementsystem.adrian.repositories.CategoryRepository;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Cacheable(value = "CategoryCache", unless = "#result == null")
    public List<CategoryDTO> getAllCategories() {
        logger.info("Fetching all categories");
        List<Category> categories = categoryRepository.findAll();
        logger.debug("Fetched {} categories", categories.size());
        return categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "CategoryCache", unless = "#result == null")
    public CategoryDTO getCategoryById(Long id) {
        logger.info("Fetching category by id: {}", id);
        Category category = fetchCategoryById(id);
        return categoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        logger.info("Saving category: {}", categoryDTO);
        Category category = categoryMapper.toCategory(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        logger.debug("Category saved with id: {}", savedCategory.getId());
        return categoryMapper.toDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        Category category = fetchCategoryById(categoryDTO.getId());
        Category updatedCategory = updateFields(category, categoryDTO);
        logger.debug("Category updated with id: {}", updatedCategory.getId());
        return categoryMapper.toDTO(categoryRepository.save(updatedCategory));
    }

    @Override
    public void deleteCategory(Long id) {
        logger.info("Deleting category with id: {}", id);
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            logger.debug("Category with id: {} deleted", id);
        } else {
            logger.warn("Category with id: {} not found for deletion", id);
            throw new EntityNotFoundException("Category with id: " + id + " not found");
        }
    }

    private Category fetchCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            logger.warn("Category with id: {} not found", id);
            return new EntityNotFoundException("Category with id: " + id + " not found");
        });
    }

    private Category updateFields(Category category, CategoryDTO categoryDTO) {
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }
}
