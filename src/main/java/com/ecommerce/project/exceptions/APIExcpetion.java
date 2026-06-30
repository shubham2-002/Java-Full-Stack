package com.ecommerce.project.exceptions;

public class APIExcpetion extends RuntimeException{

    public APIExcpetion(){
    }

    public APIExcpetion(String message){
        super(message);
    }
}
