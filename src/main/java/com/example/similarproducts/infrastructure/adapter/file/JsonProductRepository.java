package com.example.similarproducts.infrastructure.adapter.file;

import com.example.similarproducts.domain.exception.ProductNotFoundException;
import com.example.similarproducts.domain.exception.ProductServiceUnavailableException;
import com.example.similarproducts.domain.model.Product;
import com.example.similarproducts.domain.port.ProductRepositoryPort;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JsonProductRepository implements ProductRepositoryPort {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<ProductWrapper> mocks;

    public JsonProductRepository() {
        try (InputStream is = getClass().getResourceAsStream("/mocks.json")) {
            this.mocks = objectMapper.readValue(is, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo productos.json", e);
        }
    }

    @Override
    public List<Product> findSimilarProducts(String productId) {
        // Buscar los IDs similares en el mock
        ProductWrapper similarIdsEntry = findMock("/product/" + productId + "/similarids")
                .orElseThrow(() -> new ProductNotFoundException(productId));

        try {
            List<String> ids = objectMapper.readValue(similarIdsEntry.getBody(), new TypeReference<>() {});
            return ids.stream()
                    .map(this::findProductById)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error procesando similares para " + productId, e);
        }
    }

    private Optional<Product> findProductById(String productId) {
        Optional<ProductWrapper> entry = findMock("/product/" + productId);

        if (entry.isEmpty()) return Optional.empty();

        ProductWrapper wrapper = entry.get();

        if (wrapper.getStatus() != null) {
            switch (wrapper.getStatus()) {
                case 404 -> {return Optional.empty();}
                case 500 -> throw new ProductServiceUnavailableException(productId);
                default -> throw new RuntimeException("Unexpected status " + wrapper.getStatus() + " for product " + productId);
            }
        }

        try {
            return Optional.of(objectMapper.readValue(wrapper.getBody(), Product.class));
        } catch (Exception e) {
            throw new RuntimeException("Error parseando producto " + productId, e);
        }
    }

    private Optional<ProductWrapper> findMock(String path) {
        return mocks.stream()
                .filter(m -> m.getPath().equals(path))
                .findFirst();
    }
}
