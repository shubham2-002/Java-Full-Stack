package com.ecommerce.project.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jdk.jfr.Enabled;

@Entity(name = "categories")// tell taht this class is now enity in database  or table in database
public class Category {
    @Id   // have to specfiy ID otherwsie will gave error as no unique identifer
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //Database generates the ID using an auto-increment column.
    private Long categoryId;
    private String categoryName;

    public Category() {
        // by adding this i will not get  No default constructor for entity
        //The error:
        //No default constructor for entity 'com.ecommerce.project.model.Category'
        //means that JPA/Hibernate is trying to create a Category object,
        // but your entity does not have a no-argument (default) constructor.
    }
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
