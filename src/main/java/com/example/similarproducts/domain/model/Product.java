package com.example.similarproducts.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private String id;
    private String name;
    private double price;
    private boolean availability;
    private String message;


    public Product() {
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public String getMessage() {
        return message;
    }
}
