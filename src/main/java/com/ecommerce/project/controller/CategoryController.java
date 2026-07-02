package com.ecommerce.project.controller;


import com.ecommerce.project.config.AppConstant;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired   // feild Injection
    private CategoryService categoryService;



    // using Constucotr Injection
//    public CategoryController(CategoryService categoryService) {
//        this.categoryService=categoryService;
//    }

//    @GetMapping("api/public/categories")  another way of writing controller
    @RequestMapping(value = "/public/categories",method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse>
    getAllCategories(
    @RequestParam(name="pageNumber" ,defaultValue = AppConstant.PAGE_NUMBER,required = false)Integer pageNumber,
    @RequestParam(name="pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false)Integer pageSize,
    @RequestParam(name = "sortBy",defaultValue = AppConstant.SORT_CATEGORIES_BY,required = false) String sortBy,
    @RequestParam(name = "sortOrder",defaultValue = AppConstant.SORT_DIRECTION,required = false) String sortOrder

    ) {

        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);

        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }


    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategory =categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategory,HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity <CategoryDTO> deleteCategory(@PathVariable Long categoryId){
       CategoryDTO deletedCategory = categoryService.deleteCategory(categoryId);
       return  new ResponseEntity<>(deletedCategory,HttpStatus.OK);
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO>updateCategory(@RequestBody CategoryDTO categoryDTO ,@PathVariable Long categoryId){

            CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO,categoryId);
            return  new ResponseEntity<>(savedCategoryDTO,HttpStatus.OK);

    }
}
