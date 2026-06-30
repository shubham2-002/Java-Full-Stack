package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired   // feild Injection
    private CategoryService categoryService;


    // using Constucotr Injection
//    public CategoryController(CategoryService categoryService) {
//        this.categoryService=categoryService;
//    }


//    @GetMapping("api/public/categories")  another way of writing controller
    @RequestMapping(value = "api/public/categories",method = RequestMethod.GET)
    public ResponseEntity<List<Category>>  getAllCategories() {
        List<Category>allCategories = categoryService.getAllCategories();

        return new ResponseEntity<>(allCategories,HttpStatus.OK);
    }


    @PostMapping("api/public/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category added sucessfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity <String> deleteCategory(@PathVariable Long categoryId){
        try{
            String status = categoryService.deleteCategory(categoryId);
//            more way to write response eintyt
            return new ResponseEntity<>(status, HttpStatus.OK);
//            return ResponseEntity.ok(status);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(status);
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }

    @PutMapping("/api/public/categories/{categoryId}")
    public ResponseEntity<String>updateCategory(@RequestBody Category categroy ,@PathVariable Long categoryId){

            Category savedCategory = categoryService.updateCategory(categroy,categoryId);
            return  new ResponseEntity<>("Category updated sucessfully with ID: "+categoryId,HttpStatus.OK);

    }
}
