package com.ecommerce.project.exceptions;


//Class for custom error handler

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


//solves this by providing a single class where
// you can map specific exceptions to specific error-handling methods.
@RestControllerAdvice

public class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String ,String>> myMethodArgumentnotValidExcpetion(MethodArgumentNotValidException ex){
        Map<String ,String> response = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err->{
            String fieldName=((FieldError)err).getField();
            String Message=err.getDefaultMessage();
            response.put(fieldName,Message);
        });
        return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> myResourceNotFoundException(ResourceNotFoundException e){
        String message=e.getMessage();
        return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(APIExcpetion.class)
    public  ResponseEntity<String> myAPIException(APIExcpetion e){
        String message=e.getMessage();
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);

    }
}
