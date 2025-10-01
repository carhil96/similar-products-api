package com.example.similarproducts.application.service;

import com.example.similarproducts.domain.model.ProductDetail;
import com.example.similarproducts.domain.port.ProductRepositoryPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GetSimilarProductsService {

    private final ProductRepositoryPort productRepository;

    public GetSimilarProductsService(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<ProductDetail> execute(String productId) {
        return productRepository.findSimilarProducts(productId);
    }
}
