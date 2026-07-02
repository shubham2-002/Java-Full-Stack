package com.ecommerce.project.service;

import com.ecommerce.project.Repository.CategoryRepo;
import com.ecommerce.project.exceptions.APIExcpetion;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service   //<<-- Tells spring to manage this compnoent as bean and inject it
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize ,String sortBy,String sortOrder) {

        Sort sortByAndOrder= sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);

        Page<Category> categoryPage = categoryRepo.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();

        if(categories.isEmpty()){
            throw new APIExcpetion("No Category exists");
        }
        //Using ModelMapper to convert to object of type CategoryDTO
        List<CategoryDTO> categoryDTO = categories.stream().
                map(category -> modelMapper.map(category, CategoryDTO.class)).toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTO);
        //pages Metadata
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDB=categoryRepo.findByCategoryName(category.getCategoryName());
        if(categoryFromDB!=null){
            throw new APIExcpetion("Category with name "+category.getCategoryName() +" already exists");
        }
//        category.setCategoryId(nextId++);
//        categories.add(category);
      Category savedCategory=  categoryRepo.save(category);
        return modelMapper.map( savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
//        List<Category> categories=categoryRepo.findAll();
        Optional<Category> deleteCategoyOptional=categoryRepo.findById(categoryId);

//        Category deleteCategory=deleteCategoyOptional.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found"));
        Category deleteCategory=deleteCategoyOptional.orElseThrow(()->new ResourceNotFoundException("Category","CategoryID",categoryId));

//        categories.remove(category);
        categoryRepo.delete(deleteCategory);
        return  modelMapper.map(deleteCategory,CategoryDTO.class);
    }


    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category  savedCategoy=categoryRepo.findById(categoryId).orElseThrow
                (()->new ResourceNotFoundException("Category","CategoryID",categoryId));

        Category category =modelMapper.map(categoryDTO, Category.class);
        category.setCategoryId(categoryId);
        savedCategoy = categoryRepo.save(category);
        return modelMapper.map(savedCategoy, CategoryDTO.class);

        //Updation part


    }
}
