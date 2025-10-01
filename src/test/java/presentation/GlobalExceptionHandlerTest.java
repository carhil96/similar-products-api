package presentation;

import com.example.similarproducts.infrastructure.exception.ProductNotFoundException;
import com.example.similarproducts.presentation.GlobalExceptionHandler;
import com.example.similarproducts.presentation.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleProductNotFound() {
        ProductNotFoundException ex = new ProductNotFoundException("999");
        ResponseEntity<ErrorResponse> response = handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Not Found", response.getBody().error());
        assertEquals("Product not found: 999", response.getBody().message());
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    void testHandleGeneralException() {
        Exception ex = new RuntimeException("Error inesperado");
        ResponseEntity<ErrorResponse> response = handler.handleGeneral(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Internal Server Error", response.getBody().error());
        assertEquals("Internal Server Error", response.getBody().message());
        assertNotNull(response.getBody().timestamp());
    }
}
