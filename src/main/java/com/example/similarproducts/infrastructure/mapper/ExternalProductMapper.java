package com.example.similarproducts.infrastructure.mapper;

import com.example.similarproducts.domain.model.ProductDetail;
import com.example.similarproducts.infrastructure.dto.ExternalProductResponse;

public class ExternalProductMapper {

    public static ProductDetail toDomain(ExternalProductResponse external) {
        return new ProductDetail(
                external.getId(),
                external.getName(),
                external.getPrice(),
                external.isAvailability()
        );
    }
}
