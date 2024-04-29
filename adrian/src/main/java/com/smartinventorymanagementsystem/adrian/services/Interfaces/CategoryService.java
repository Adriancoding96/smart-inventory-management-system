package com.smartinventorymanagementsystem.adrian.services.Interfaces;

import com.smartinventorymanagementsystem.adrian.dtos.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Long id);

    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}
