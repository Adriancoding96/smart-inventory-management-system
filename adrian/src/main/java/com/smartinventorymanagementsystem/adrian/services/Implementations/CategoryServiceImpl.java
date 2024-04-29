package com.smartinventorymanagementsystem.adrian.services.Implementations;

import com.smartinventorymanagementsystem.adrian.dtos.CategoryDTO;
import com.smartinventorymanagementsystem.adrian.mappers.CategoryMapper;
import com.smartinventorymanagementsystem.adrian.models.Category;
import com.smartinventorymanagementsystem.adrian.repositories.CategoryRepository;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;


    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()) {
            return categoryMapper.toDTO(category.get());
        } else {
            throw new EntityNotFoundException("Category with id: " + id + " not found");
        }
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toCategory(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        Optional<Category> category = categoryRepository.findById(categoryDTO.getId());
        if(category.isPresent()) {
            Category updatedCategory = updateFields(category.get(), categoryDTO);
            return categoryMapper.toDTO(categoryRepository.save(updatedCategory));
        } else {
            throw new EntityNotFoundException("Category with id: " + categoryDTO.getId() + " not found");
        }

    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private Category updateFields(Category category, CategoryDTO categoryDTO) {
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }
}
