package com.ecommerce.project.Repository;

import com.ecommerce.project.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
// this jpa repo gives more funcationlty specif to jpa and it is Extesion// children of Main spirng interface crudRepos
//This is better choice whe using JPA

//                                           Class  Datatype of PrimaryKey
public interface CategoryRepo extends JpaRepository<Category,Long>{
     Category findByCategoryName(String categoryName);



}
