package com.example.similarproducts.entrypoint.rest;

import com.example.similarproducts.application.service.GetSimilarProductsService;
import com.example.similarproducts.domain.model.ProductDetail;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/product")
public class ProductController {

    private final GetSimilarProductsService service;

    public ProductController(GetSimilarProductsService service) {
        this.service = service;
    }

    @GetMapping("/{productId}/similar")
    public Flux<ProductDetail> getSimilarProducts(@PathVariable String productId) {
        return service.execute(productId);
    }

}
