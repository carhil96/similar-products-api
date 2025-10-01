package entrypoint.rest;

import com.example.similarproducts.application.service.GetSimilarProductsService;
import com.example.similarproducts.domain.model.ProductDetail;
import com.example.similarproducts.entrypoint.rest.ProductController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

class ProductControllerTest {

    @Test
    void testGetSimilarProducts() {
        GetSimilarProductsService serviceMock = Mockito.mock(GetSimilarProductsService.class);

        Flux<ProductDetail> mockFlux = Flux.just(
                new ProductDetail("1", "Shirt", 9.99, true),
                new ProductDetail("2", "Dress", 19.99, true)
        );

        Mockito.when(serviceMock.execute("1")).thenReturn(mockFlux);

        WebTestClient client = WebTestClient.bindToController(new ProductController(serviceMock)).build();

        client.get()
                .uri("/product/1/similar")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo("1")
                .jsonPath("$[0].name").isEqualTo("Shirt")
                .jsonPath("$[1].id").isEqualTo("2")
                .jsonPath("$[1].name").isEqualTo("Dress");
    }
}
