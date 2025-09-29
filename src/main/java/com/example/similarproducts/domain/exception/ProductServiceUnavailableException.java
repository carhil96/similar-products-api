package com.example.similarproducts.domain.exception;

public class ProductServiceUnavailableException extends RuntimeException {
    public ProductServiceUnavailableException(String productId) {
        super("Product service unavailable for id " + productId);
    }
}
