package easytime.bff.api.infra.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    @Test
    void handleHttpMessageNotReadableException_returnsBadRequestWithMessage() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Invalid body");
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/api/test");

        ResponseEntity<Object> response = handler.handleHttpMessageNotReadableException(ex, request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("O corpo da requisição está ausente.", response.getBody());
    }
}