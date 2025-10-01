package infrastructure.adapter.file;


import com.example.similarproducts.infrastructure.adapter.file.HttpProductRepository;
import com.example.similarproducts.infrastructure.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class HttpProductRepositoryTest {

    private HttpProductRepository repository;

    @BeforeEach
    void setup() {
        ExchangeFunction exchangeFunction = request -> {
            String path = request.url().getPath();

            // Mock similarids
            if (path.equals("/product/1/similarids")) {
                return Mono.just(ClientResponse.create(HttpStatusCode.valueOf(200))
                        .header("Content-Type", "application/json")
                        .body("[2,3]")
                        .build());
            }

            // Mock detalles de productos
            if (path.equals("/product/2")) {
                return Mono.just(ClientResponse.create(HttpStatusCode.valueOf(200))
                        .header("Content-Type", "application/json")
                        .body("{\"id\":\"2\",\"name\":\"Dress\",\"price\":19.99,\"availability\":true}")
                        .build());
            }
            if (path.equals("/product/3")) {
                // Error 500 → placeholder
                return Mono.error(new RuntimeException("500 Internal Server Error"));
            }

            // Default 404
            return Mono.just(ClientResponse.create(HttpStatusCode.valueOf(404)).build());
        };

        WebClient webClient = WebClient.builder()
                .exchangeFunction(exchangeFunction)
                .build();

        repository = new HttpProductRepository(webClient);
    }

    @Test
    void testFindSimilarProductsAllValid() {
        StepVerifier.create(repository.findSimilarProducts("1"))
                .expectNextMatches(p -> p.name().equals("Dress"))          // id 2 válido
                .expectNextMatches(p -> p.name().equals("No disponible"))  // id 3 placeholder
                .verifyComplete();
    }

    @Test
    void testFindSimilarProductsNotFound() {
        WebClient webClient = WebClient.builder()
                .exchangeFunction(request -> Mono.just(ClientResponse.create(HttpStatusCode.valueOf(404)).build()))
                .build();
        repository = new HttpProductRepository(webClient);

        StepVerifier.create(repository.findSimilarProducts("unknown"))
                .expectErrorMatches(throwable ->
                        throwable instanceof ProductNotFoundException &&
                                throwable.getMessage().contains("unknown"))
                .verify();
    }

    @Test
    void testFindSimilarProductsWithPlaceholders() {
        StepVerifier.create(repository.findSimilarProducts("1"))
                .expectNextMatches(p -> p.name().equals("Dress"))          // id 2 válido
                .expectNextMatches(p -> p.name().equals("No disponible"))  // id 3 error → placeholder
                .verifyComplete();
    }
}
