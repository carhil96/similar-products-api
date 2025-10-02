package com.example.similarproducts.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {
    private String id;
    private String name;
    private double price;
    private boolean availability;
}
