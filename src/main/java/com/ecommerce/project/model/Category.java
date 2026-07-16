package com.ecommerce.project.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Entity(name = "categories")// tell taht this class is now enity in database  or table in database
@Data  // lombok
@NoArgsConstructor // lombok
@AllArgsConstructor // lombok
public class Category {

    @Id   // have to specfiy ID otherwsie will gave error as no unique identifer
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //Database generates the ID using an auto-increment column.
    private Long categoryId;

    @NotBlank
    @Size(min = 5,message = "Category Name must be least 5 Character")
    private String categoryName;

    @OneToMany(mappedBy = "category" ,cascade = CascadeType.ALL)
    private List<Product> products;

}
