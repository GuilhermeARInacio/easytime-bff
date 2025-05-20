package easytime.bff.api.controller;

import easytime.bff.api.dto.pontos.ConsultaPontoDTO;
import easytime.bff.api.dto.usuario.LoginDto;
import easytime.bff.api.service.PontoService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PontoControllerTest {

    @InjectMocks
    private PontoController pontoController;

    @Mock
    private PontoService pontoService;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarPonto_Success() {
        // Arrange
        LoginDto loginDto = new LoginDto("");

        when(pontoService.registrarPonto(any(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(""));

        // Act
        var response = pontoController.registrarPonto(loginDto, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRegistrarPonto_InvalidLogin() {
        // Arrange
        LoginDto loginDto = new LoginDto(""); // LoginDto vazio

        // Act
        var response = pontoController.registrarPonto(loginDto, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    void testExcluirPonto_Success() {
        when(pontoService.deletarPonto(anyInt(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body("Ponto excluído com sucesso"));

        // Act
        var response = pontoController.excluirPonto(1, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testExcluirPonto_Exception() {
        when(pontoService.deletarPonto(anyInt(), any()))
                .thenThrow(new RuntimeException("Erro ao excluir ponto"));

        // Act
        var response = pontoController.excluirPonto(1, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testConsultaPonto_Success() {
        ConsultaPontoDTO dto = new ConsultaPontoDTO("user", "2023-01-01", "2023-01-31");

        when(pontoService.consultarPonto(any(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(""));

        // Act
        var response = pontoController.consultaPonto(dto, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testConsultaPonto_InvalidLogin() {
        ConsultaPontoDTO dto = new ConsultaPontoDTO("", "2023-01-01", "2023-01-31");

        // Act
        var response = pontoController.consultaPonto(dto, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testConsultaPonto_Exception() {
        ConsultaPontoDTO dto = new ConsultaPontoDTO("user", "2023-01-01", "2023-01-31");

        when(pontoService.consultarPonto(dto, request))
                .thenThrow(HttpClientErrorException.NotFound.class);

        // Act
        var response = pontoController.consultaPonto(dto, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testConsultaUsuarioNaoEncontrado() {
        ConsultaPontoDTO dto = new ConsultaPontoDTO("user", "2023-01-01", "2023-01-31");

        when(pontoService.consultarPonto(dto, request))
                .thenThrow(HttpClientErrorException.Unauthorized.class);

        // Act
        var response = pontoController.consultaPonto(dto, request);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}