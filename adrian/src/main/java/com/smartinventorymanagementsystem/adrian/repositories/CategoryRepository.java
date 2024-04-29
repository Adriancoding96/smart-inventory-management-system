package com.smartinventorymanagementsystem.adrian.repositories;

import com.smartinventorymanagementsystem.adrian.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
