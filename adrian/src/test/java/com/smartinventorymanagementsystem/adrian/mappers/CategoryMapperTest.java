package com.smartinventorymanagementsystem.adrian.mappers;


import com.smartinventorymanagementsystem.adrian.dtos.CategoryDTO;
import com.smartinventorymanagementsystem.adrian.models.Category;
import com.smartinventorymanagementsystem.adrian.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class CategoryMapperTest {
    private CategoryMapper categoryMapper;

    @Autowired
    public CategoryMapperTest(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Test
    void convertCategoryEntityToDTO() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Keyboards");

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Keyboard1");
        product1.setDescription("Description");
        product1.setPrice(new BigDecimal(80.0));
        product1.setStockQuantity(10);
        product1.setCategory(category);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Keyboard2");
        product2.setDescription("Description");
        product2.setPrice(new BigDecimal(80.0));
        product2.setStockQuantity(10);
        product2.setCategory(category);

        Set<Product> products = new HashSet<>();
        products.add(product1);
        products.add(product2);
        category.setProducts(products);

        CategoryDTO categoryDTO = categoryMapper.toDTO(category);
        assertEquals(category.getId(), categoryDTO.getId());
        assertEquals(category.getName(), categoryDTO.getName());
        assertEquals(category.getDescription(), categoryDTO.getDescription());
    }

    @Test
    void convertDTOToCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Keyboards");
        categoryDTO.setDescription("Description");

        Category category = categoryMapper.toCategory(categoryDTO);
        assertEquals(category.getId(), categoryDTO.getId());
        assertEquals(category.getName(), categoryDTO.getName());
        assertEquals(category.getDescription(), categoryDTO.getDescription());
    }
}
