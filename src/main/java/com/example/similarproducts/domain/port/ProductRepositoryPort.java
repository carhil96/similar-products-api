package com.example.similarproducts.domain.port;

import com.example.similarproducts.domain.model.Product;
import java.util.List;

public interface ProductRepositoryPort {
    List<Product> findSimilarProducts(String productId);
}
