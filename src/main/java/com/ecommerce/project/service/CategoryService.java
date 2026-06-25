package com.ecommerce.project.service;


import com.ecommerce.project.model.Category;

import java.util.List;

// can be declred as class as well but for acheving loose coupling we are making it interface


public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long categoryId);
    Category updateCategory(Category category ,Long categoryId);
}
