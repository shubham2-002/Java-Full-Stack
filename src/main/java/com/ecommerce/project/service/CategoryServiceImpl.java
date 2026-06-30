package com.ecommerce.project.service;

import com.ecommerce.project.Repository.CategoryRepo;
import com.ecommerce.project.exceptions.APIExcpetion;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
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


    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        if(categories.isEmpty()){
            throw new APIExcpetion("No Category exists");
        }
        return categories;
    }


    @Override
    public void createCategory(Category category) {
        Category savedCategory=categoryRepo.findByCategoryName(category.getCategoryName());
        if(savedCategory!=null){
            throw new APIExcpetion("Category with name "+category.getCategoryName() +" already exists");
        }
//        category.setCategoryId(nextId++);
//        categories.add(category);
        categoryRepo.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
//        List<Category> categories=categoryRepo.findAll();
        Optional<Category> deleteCategoyOptional=categoryRepo.findById(categoryId);

//        Category deleteCategory=deleteCategoyOptional.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found"));
        Category deleteCategory=deleteCategoyOptional.orElseThrow(()->new ResourceNotFoundException("Category","CategoryID",categoryId));

//        categories.remove(category);
        categoryRepo.delete(deleteCategory);
        return "Category deleted successfully Id "+categoryId;
    }


    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Category  savedCategoy=categoryRepo.findById(categoryId).orElseThrow
                (()->new ResourceNotFoundException("Category","CategoryID",categoryId));

        category.setCategoryId(categoryId);
        savedCategoy = categoryRepo.save(category);
        return savedCategoy;

        //Updation part


    }
}
