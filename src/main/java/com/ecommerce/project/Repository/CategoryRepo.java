package com.ecommerce.project.Repository;

import com.ecommerce.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
// this jpa repo gives more funcationlty specif to jpa and it is Extesion// children of Main spirng interface crudRepos
//This is better choice whe using JPA

//                                           Class  Datatype of PrimaryKey
public interface CategoryRepo extends JpaRepository<Category,Long>{
}
