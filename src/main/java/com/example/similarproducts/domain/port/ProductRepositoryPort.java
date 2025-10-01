package com.example.similarproducts.domain.port;

import com.example.similarproducts.domain.model.ProductDetail;
import reactor.core.publisher.Flux;

public interface ProductRepositoryPort {
    Flux<ProductDetail> findSimilarProducts(String productId);
}
