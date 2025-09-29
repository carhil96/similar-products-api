package com.example.similarproducts.entrypoint.rest;

import com.example.similarproducts.application.service.GetSimilarProductsService;
import com.example.similarproducts.domain.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final GetSimilarProductsService service;

    public ProductController(GetSimilarProductsService service) {
        this.service = service;
    }

    @GetMapping("/{productId}/similar")
    public ResponseEntity<List<Product>> getSimilarProducts(@PathVariable String productId) {
        try {
            List<Product> products = service.execute(productId);
            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
