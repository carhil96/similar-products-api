package com.example.similarproducts.infrastructure.config;

import com.example.similarproducts.application.service.GetSimilarProductsService;
import com.example.similarproducts.domain.port.ProductRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public GetSimilarProductsService getSimilarProductsService(ProductRepositoryPort repository) {
        return new GetSimilarProductsService(repository);
    }
}
