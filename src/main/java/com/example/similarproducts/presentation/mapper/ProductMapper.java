package com.example.similarproducts.presentation.mapper;

import com.example.similarproducts.domain.model.ProductDetail;
import com.example.similarproducts.presentation.dto.ProductDetailDto;

public class ProductMapper {

    public static ProductDetailDto toDto(ProductDetail domain) {
        return new ProductDetailDto(
                domain.id(),
                domain.name(),
                domain.price(),
                domain.availability()
        );
    }

    public static ProductDetail toDomain(ProductDetailDto dto) {
        return new ProductDetail(
                dto.getId(),
                dto.getName(),
                dto.getPrice(),
                dto.isAvailability()
        );
    }
}
