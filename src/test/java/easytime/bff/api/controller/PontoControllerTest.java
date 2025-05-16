package easytime.bff.api.controller;

import easytime.bff.api.dto.usuario.LoginDto;
import easytime.bff.api.service.PontoService;
import easytime.bff.api.util.ExceptionHandlerUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PontoControllerTest {

    @InjectMocks
    private PontoController pontoController;

    @Mock
    private PontoService pontoService;


    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarPonto_Success() {
        // Arrange
        LoginDto loginDto = new LoginDto("");

        ResponseEntity<?> mockResponse = ResponseEntity.ok("Ponto registrado com sucesso");

        when(pontoService.registrarPonto(new LoginDto(""), httpServletRequest)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(""));
        // Act
        ResponseEntity<?> response = pontoController.registrarPonto(loginDto, httpServletRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ponto registrado com sucesso", response.getBody());
        verify(pontoService, times(1)).registrarPonto(eq(loginDto), any(HttpServletRequest.class));
    }

    @Test
    void testRegistrarPonto_InvalidLogin() {
        // Arrange
        LoginDto loginDto = new LoginDto(""); // LoginDto vazio

        // Act
        ResponseEntity<?> response = pontoController.registrarPonto(loginDto, httpServletRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(logger, times(1)).error(anyString(), eq((Object) null), any(IllegalArgumentException.class));
    }

    @Test
    void testRegistrarPonto_Exception() {
        // Arrange
        LoginDto loginDto = new LoginDto("user");

        when(pontoService.registrarPonto(eq(loginDto), any(HttpServletRequest.class)))
                .thenThrow(new RuntimeException("Erro inesperado"));

        // Act
        ResponseEntity<?> response = pontoController.registrarPonto(loginDto, httpServletRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(logger, times(1)).error(anyString(), eq("user"), any(RuntimeException.class));
    }

    @Test
    void testExcluirPonto_Success() {
        // Arrange
        Integer pontoId = 123;

        ResponseEntity<String> mockResponse = ResponseEntity.ok("Ponto excluído com sucesso");
        when(pontoService.deletarPonto(eq(pontoId), any(HttpServletRequest.class))).thenReturn(mockResponse);

        // Act
        ResponseEntity<?> response = pontoController.excluirPonto(pontoId, httpServletRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ponto excluído com sucesso", response.getBody());
        verify(pontoService, times(1)).deletarPonto(eq(pontoId), any(HttpServletRequest.class));
    }

    @Test
    void testExcluirPonto_Exception() {
        // Arrange
        Integer pontoId = 123;

        when(pontoService.deletarPonto(eq(pontoId), any(HttpServletRequest.class)))
                .thenThrow(new RuntimeException("Erro ao excluir ponto"));

        // Act
        ResponseEntity<?> response = pontoController.excluirPonto(pontoId, httpServletRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(logger, times(1)).error(anyString(), eq(pontoId), any(RuntimeException.class));
    }
}