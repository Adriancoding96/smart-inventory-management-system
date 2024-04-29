package com.smartinventorymanagementsystem.adrian.mappers;

import com.smartinventorymanagementsystem.adrian.dtos.CategoryDTO;
import com.smartinventorymanagementsystem.adrian.models.Category;
import org.hibernate.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    private ModelMapper modelMapper;

    @Autowired
    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoryDTO toDTO(Category category) {
        try {
            CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
            return categoryDTO;
        } catch ( Exception exception) {
            throw new MappingException("Unable to map Category to CategoryDTO", exception);
        }
    }

    public Category toCategory(CategoryDTO categoryDTO) {
        try {
            Category category = modelMapper.map(categoryDTO, Category.class);
            return category;
        } catch(Exception exception) {
            throw new MappingException("Unable to map CategoryDTO to Category", exception);
        }
    }

}
