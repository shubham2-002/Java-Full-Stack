package com.ecommerce.project.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//THis is represting data on presentation layer
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long categoryID;
    private String categoryName;

}
