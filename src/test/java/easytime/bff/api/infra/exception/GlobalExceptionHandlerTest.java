package easytime.bff.api.infra.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    @Test
    void handleValidationExceptions_returnsBadRequestWithFieldErrors() {
        // Arrange
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("object", "field1", "must not be null");
        FieldError fieldError2 = new FieldError("object", "field2", "must be a number");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleValidationExceptions(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errors = response.getBody();
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertEquals("must not be null", errors.get("field1"));
        assertEquals("must be a number", errors.get("field2"));
    }

    @Test
    void handleHttpMessageNotReadableException_returnsBadRequestWithMessage() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Invalid body");
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/api/test");

        ResponseEntity<Object> response = handler.handleHttpMessageNotReadableException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O corpo da requisição está ausente.", response.getBody());
    }
}