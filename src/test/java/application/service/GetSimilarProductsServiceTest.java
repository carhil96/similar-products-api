package application.service;

import com.example.similarproducts.application.service.GetSimilarProductsService;
import com.example.similarproducts.domain.model.ProductDetail;
import com.example.similarproducts.domain.port.ProductRepositoryPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetSimilarProductsServiceTest {

    private ProductRepositoryPort repositoryMock;
    private GetSimilarProductsService service;

    @BeforeEach
    void setup() {
        repositoryMock = mock(ProductRepositoryPort.class);
        service = new GetSimilarProductsService(repositoryMock);
    }

    @Test
    void testExecuteReturnsProductsFlux() {
        Flux<ProductDetail> mockProducts = Flux.just(
                new ProductDetail("1", "Shirt", 9.99, true),
                new ProductDetail("2", "Dress", 19.99, true)
        );

        when(repositoryMock.findSimilarProducts("1")).thenReturn(mockProducts);

        List<ProductDetail> result = service.execute("1").collectList().block();

        Assertions.assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Shirt", result.get(0).name());
        assertEquals("Dress", result.get(1).name());

        verify(repositoryMock, times(1)).findSimilarProducts("1");
    }
}
