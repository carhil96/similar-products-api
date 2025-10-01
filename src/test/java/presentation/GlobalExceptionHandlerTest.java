package presentation;

import com.example.similarproducts.infrastructure.exception.ProductNotFoundException;
import com.example.similarproducts.presentation.GlobalExceptionHandler;
import com.example.similarproducts.presentation.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleProductNotFound() {
        ProductNotFoundException ex = new ProductNotFoundException("999");
        ResponseEntity<ErrorResponse> response = handler.handleNotFound(ex);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not Found", response.getBody().error());
        assertEquals("Product not found: 999", response.getBody().message());
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    void testHandleGeneralException() {
        Exception ex = new RuntimeException("Error inesperado");
        ResponseEntity<ErrorResponse> response = handler.handleGeneral(ex);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Internal Server Error", response.getBody().error());
        assertEquals("Internal Server Error", response.getBody().message());
        assertNotNull(response.getBody().timestamp());
    }
}
