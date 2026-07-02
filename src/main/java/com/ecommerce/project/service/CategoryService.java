package com.ecommerce.project.service;


import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

// can be declred as class as well but for acheving loose coupling we are making it interface


public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
