package com.example.similarproducts.infrastructure.dto;

import lombok.Data;

@Data
public class ExternalProductResponse {
    private String id;
    private String name;
    private double price;
    private boolean availability;
}
