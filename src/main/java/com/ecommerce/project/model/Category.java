package com.ecommerce.project.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jdk.jfr.Enabled;

@Entity(name = "categories")// tell taht this class is now enity in database  or table in database
public class Category {
    @Id   // have to specfiy ID otherwsie will gave error as no unique identifer
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //this strgy uses identity col in database to generate primkey
    private Long categoryId;
    private String categoryName;

    public Category(Long categoryId,String categoryName) {
        this.categoryId = categoryId;;
        this.categoryName = categoryName;
    }

    public String getCategoryName(){
        return categoryName;
    }

    public void setCategoryName(String categoryName){
        this.categoryName = categoryName;
    }

    public Long getCategoryId(){
        return categoryId;
    }
    public void setCategoryId(Long categoryId){
        this.categoryId = categoryId;
    }
}
