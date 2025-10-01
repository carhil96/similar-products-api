package application.service;

import com.example.similarproducts.application.service.GetSimilarProductsService;
import com.example.similarproducts.domain.model.ProductDetail;
import com.example.similarproducts.domain.port.ProductRepositoryPort;
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
        // 1️⃣ Simular Flux de productos
        Flux<ProductDetail> mockProducts = Flux.just(
                new ProductDetail("1", "Shirt", 9.99, true),
                new ProductDetail("2", "Dress", 19.99, true)
        );

        // 2️⃣ Configurar el mock para que devuelva el Flux
        when(repositoryMock.findSimilarProducts("1")).thenReturn(mockProducts);

        // 3️⃣ Ejecutar el servicio y recolectar el Flux en lista
        List<ProductDetail> result = service.execute("1").collectList().block();

        // 4️⃣ Asserts
        assertEquals(2, result.size());
        assertEquals("Shirt", result.get(0).name());
        assertEquals("Dress", result.get(1).name());

        // 5️⃣ Verificar que el método del mock fue llamado
        verify(repositoryMock, times(1)).findSimilarProducts("1");
    }
}
