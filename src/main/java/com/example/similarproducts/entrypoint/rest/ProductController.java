package com.example.similarproducts.entrypoint.rest;

import com.example.similarproducts.application.service.GetSimilarProductsService;
import com.example.similarproducts.presentation.dto.ProductDetailDto;
import com.example.similarproducts.presentation.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final GetSimilarProductsService service;

    @GetMapping("/{productId}/similar")
    public Flux<ProductDetailDto> getSimilarProducts(@PathVariable String productId) {
        return service.execute(productId)
                .map(ProductMapper::toDto);
    }


}
