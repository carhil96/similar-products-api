package com.example.similarproducts.infrastructure.adapter.file;

import com.example.similarproducts.domain.model.ProductDetail;
import com.example.similarproducts.domain.port.ProductRepositoryPort;
import com.example.similarproducts.infrastructure.exception.ProductNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Repository
public class HttpProductRepository implements ProductRepositoryPort {

    private final WebClient webClient;
    private static final int MAX_CONCURRENCY = 10;
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(5);

    public HttpProductRepository(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Flux<ProductDetail> findSimilarProducts(String productId) {


        return webClient.get()
                .uri("/product/{productId}/similarids", productId)
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        response -> Mono.error(new ProductNotFoundException(productId)))
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                .flatMapMany(Flux::fromIterable) // convierte List<String> → Flux<String>
                .flatMapSequential(
                        id -> webClient.get()
                                .uri("/product/{productId}", id)
                                .retrieve()
                                .bodyToMono(ProductDetail.class)
                                .timeout(REQUEST_TIMEOUT)
                                .onErrorResume(e -> {
                                    System.err.println("Producto " + id + " no disponible: " + e.getMessage());
                                    return Mono.just(new ProductDetail(id, "No disponible", 0.0, false));
                                }),
                        MAX_CONCURRENCY
                );
    }
}
