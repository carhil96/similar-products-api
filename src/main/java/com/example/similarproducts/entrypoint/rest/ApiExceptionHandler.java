package com.example.similarproducts.entrypoint.rest;

import com.example.similarproducts.domain.exception.ProductNotFoundException;
import com.example.similarproducts.domain.exception.ProductServiceUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Void> handleNotFound(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(ProductServiceUnavailableException.class)
    public ResponseEntity<Void> handleServiceUnavailable(ProductServiceUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
