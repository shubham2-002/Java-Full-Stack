package com.ecommerce.project.service;

import com.ecommerce.project.Repository.CategoryRepo;
import com.ecommerce.project.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service   //<<-- Tells spring to manage this compnoent as bean and inject it
public class CategoryServiceImpl implements CategoryService{

    private List<Category> categories = new ArrayList<>();


    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public List<Category> getAllCategories() {
        return  categoryRepo.findAll();
//        return categories;
    }


    @Override
    public void createCategory(Category category) {
//        category.setCategoryId(nextId++);
//        categories.add(category);
        categoryRepo.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
//        List<Category> categories=categoryRepo.findAll();
        Optional<Category> deleteCategoyOptional=categoryRepo.findById(categoryId);

        Category deleteCategory=deleteCategoyOptional.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found"));

//        categories.remove(category);
        categoryRepo.delete(deleteCategory);
        return "Category deleted successfully Id "+categoryId;
    }


    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional<Category> savedCategoyOptional=categoryRepo.findById(categoryId);

        Category savedCategory = savedCategoyOptional.orElseThrow(()->
                                    new ResponseStatusException
                                            (HttpStatus.NOT_FOUND,"Resource Not Found"));

        category.setCategoryId(categoryId);
        savedCategory = categoryRepo.save(category);
        return savedCategory;

        //Updation part


    }
}
