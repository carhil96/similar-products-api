package com.example.similarproducts.application.service;

import com.example.similarproducts.domain.model.Product;
import com.example.similarproducts.domain.port.ProductRepositoryPort;

import java.util.List;

public class GetSimilarProductsService {

    private final ProductRepositoryPort repository;

    public GetSimilarProductsService(ProductRepositoryPort repository) {
        this.repository = repository;
    }

    public List<Product> execute(String productId) {
        return repository.findSimilarProducts(productId);
    }
}
