package easytime.bff.api.controller;

import easytime.bff.api.dto.pontos.*;
import easytime.bff.api.dto.usuario.LoginDto;
import easytime.bff.api.service.PontoService;
import easytime.bff.api.util.ExceptionHandlerUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.Field;
import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PontoControllerTest {

    @InjectMocks
    private PontoController pontoController;

    @Mock
    private PontoService pontoService;

    @Mock
    private HttpServletRequest request;

    private RegistroCompletoDto registroCompleto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startRegistroCompleto();
    }

    @Test
    void testRegistrarPonto_Success() {
        // Arrange
        LoginDto loginDto = new LoginDto("");
        BaterPonto dto = new BaterPonto("08:00:00");

        when(pontoService.registrarPonto(any(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(""));

        // Act
        var response = pontoController.registrarPonto(dto, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRegistrarPonto_InvalidLogin() {
        // Arrange
        LoginDto loginDto = new LoginDto(""); // LoginDto vazio
        BaterPonto dto = new BaterPonto("08:00:00");
        // Act
        var response = pontoController.registrarPonto(dto, request);

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
    void testExcluirPonto_HttpClientException() {
        when(pontoService.deletarPonto(anyInt(), any()))
                .thenThrow(HttpClientErrorException.NotFound.class);

        // Act
        var response = pontoController.excluirPonto(1, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testConsultaPonto_Success() {
        ConsultaPontoDTO dto = new ConsultaPontoDTO("login", "2023-01-01", "2023-01-31");

        when(pontoService.consultarPonto(dto, request))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(List.of(Mockito.mock(RegistroCompletoDto.class))));

        // Act
        var response = pontoController.consultaPonto(dto, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testConsultaPonto_InvalidLogin() {
        ConsultaPontoDTO dto = new ConsultaPontoDTO("login", "2023-01-01", "2023-01-31");

        // Act
        var response = pontoController.consultaPonto(dto, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testConsultaPonto_ClientException() {
        ConsultaPontoDTO dto = new ConsultaPontoDTO("login", "2023-01-01", "2023-01-31");

        when(pontoService.consultarPonto(dto, request))
                .thenThrow(HttpClientErrorException.NotFound.class);

        // Act
        var response = pontoController.consultaPonto(dto, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testConsultaPonto_Exception() {
        ConsultaPontoDTO dto = new ConsultaPontoDTO("login", "2023-01-01", "2023-01-31");

        when(pontoService.consultarPonto(dto, request))
                .thenThrow(new RuntimeException(""));

        // Act
        var response = pontoController.consultaPonto(dto, request);

        // Assert
        assertNotNull(response);
    }

    @Test
    void testConsultaUsuarioNaoEncontrado() {
        ConsultaPontoDTO dto = new ConsultaPontoDTO("login", "2023-01-01", "2023-01-31");

        when(pontoService.consultarPonto(dto, request))
                .thenThrow(HttpClientErrorException.Unauthorized.class);

        // Act
        var response = pontoController.consultaPonto(dto, request);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testAlterarPonto_Success() {
        AlterarPontoDto dto = mock(AlterarPontoDto.class);
        when(pontoService.alterarPonto(any(), any()))
                .thenReturn(ResponseEntity.ok().body(""));

        ResponseEntity<?> response = pontoController.alterarPonto(dto, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAlterarPonto_NotFound() {
        AlterarPontoDto dto = mock(AlterarPontoDto.class);
        when(pontoService.alterarPonto(any(), any()))
                .thenThrow(HttpClientErrorException.NotFound.class);

        ResponseEntity<?> response = pontoController.alterarPonto(dto, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAlterarPonto_BadRequest() {
        AlterarPontoDto dto = mock(AlterarPontoDto.class);
        when(pontoService.alterarPonto(any(), any()))
                .thenThrow(new IllegalArgumentException("Invalid"));

        ResponseEntity<?> response = pontoController.alterarPonto(dto, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testAlterarPonto_Unauthorized() {
        AlterarPontoDto dto = mock(AlterarPontoDto.class);
        when(pontoService.alterarPonto(any(), any()))
                .thenThrow(HttpClientErrorException.Unauthorized.class);

        ResponseEntity<?> response = pontoController.alterarPonto(dto, request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testListarAllPontos_Success() {
        when(pontoService.listarPontos(any()))
                .thenReturn(ResponseEntity.ok().body(List.of(registroCompleto)));

        ResponseEntity<?> response = pontoController.listarAllPontos(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testListarAllPontos_Exception() {
        when(pontoService.listarPontos(any()))
                .thenThrow(new RuntimeException("Erro"));

        ResponseEntity<?> response = pontoController.listarAllPontos(request);

        assertNotNull(response);
      }

    @Test
    void testAlterarPonto_ExceptionHandlerIsCalled() throws Exception {
        AlterarPontoDto dto = mock(AlterarPontoDto.class);
        RuntimeException ex = new RuntimeException("any error");
        when(pontoService.alterarPonto(any(), any())).thenThrow(ex);

        // Access private static LOGGER via reflection
        Field loggerField = PontoController.class.getDeclaredField("LOGGER");
        loggerField.setAccessible(true);
        Logger logger = (Logger) loggerField.get(null);

        try (MockedStatic<ExceptionHandlerUtil> staticUtil = mockStatic(ExceptionHandlerUtil.class)) {
            staticUtil.when(() -> ExceptionHandlerUtil.tratarExcecao(ex, logger))
                    .thenReturn(ResponseEntity.status(500).body("error"));

            pontoController.alterarPonto(dto, request);

            staticUtil.verify(() -> ExceptionHandlerUtil.tratarExcecao(ex, logger), times(1));
        }
    }

    @Test
    void testListarAllPedidos_Success() {
        var pedido = mock(PedidoPonto.class);
        when(pontoService.listarPedidos(any()))
                .thenReturn(ResponseEntity.ok().body(List.of(pedido)));

        ResponseEntity<?> response = pontoController.listarAllPedidos(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // listarAllPedidos - Exception
    @Test
    void testListarAllPedidos_Exception() {
        when(pontoService.listarPedidos(any()))
                .thenThrow(new RuntimeException("Erro"));

        try (MockedStatic<ExceptionHandlerUtil> staticUtil = mockStatic(ExceptionHandlerUtil.class)) {
            staticUtil.when(() -> ExceptionHandlerUtil.tratarExcecao(any(), any()))
                    .thenReturn(ResponseEntity.status(500).body("error"));

            ResponseEntity<?> response = pontoController.listarAllPedidos(request);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            staticUtil.verify(() -> ExceptionHandlerUtil.tratarExcecao(any(), any()), times(1));
        }
    }

    // listarPedidosPendentes - Success
//    @Test
//    void testListarPedidosPendentes_Success() {
//        var pedido = mock(PedidoPonto.class);
//        when(pontoService.listarPedidosPendentes(any()))
//                .thenReturn(ResponseEntity.ok().body(List.of(pedido)));
//
//        ResponseEntity<?> response = pontoController.listarPedidosPendentes(request);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }
//
//    // listarPedidosPendentes - Exception
//    @Test
//    void testListarPedidosPendentes_Exception() {
//        when(pontoService.listarPedidosPendentes(any()))
//                .thenThrow(new RuntimeException("Erro"));
//
//        try (MockedStatic<ExceptionHandlerUtil> staticUtil = mockStatic(ExceptionHandlerUtil.class)) {
//            staticUtil.when(() -> ExceptionHandlerUtil.tratarExcecao(any(), any()))
//                    .thenReturn(ResponseEntity.status(500).body("error"));
//
//            ResponseEntity<?> response = pontoController.listarPedidosPendentes(request);
//
//            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//            staticUtil.verify(() -> ExceptionHandlerUtil.tratarExcecao(any(), any()), times(1));
//        }
//    }
//
//    // aprovarPonto - Success
//    @Test
//    void testAprovarPonto_Success() {
//        when(pontoService.aprovarPonto(anyInt(), any()))
//                .thenReturn(ResponseEntity.ok().body("aprovado"));
//
//        ResponseEntity<?> response = pontoController.aprovarPonto(1, request);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("aprovado", response.getBody());
//    }
//
//    // aprovarPonto - Exception
//    @Test
//    void testAprovarPonto_Exception() {
//        when(pontoService.aprovarPonto(anyInt(), any()))
//                .thenThrow(new RuntimeException("Erro"));
//
//        try (MockedStatic<ExceptionHandlerUtil> staticUtil = mockStatic(ExceptionHandlerUtil.class)) {
//            staticUtil.when(() -> ExceptionHandlerUtil.tratarExcecao(any(), any()))
//                    .thenReturn(ResponseEntity.status(500).body("error"));
//
//            ResponseEntity<?> response = pontoController.aprovarPonto(1, request);
//
//            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//            staticUtil.verify(() -> ExceptionHandlerUtil.tratarExcecao(any(), any()), times(1));
//        }
//    }
//
//    // reprovarPonto - Success
//    @Test
//    void testReprovarPonto_Success() {
//        when(pontoService.reprovarPonto(anyInt(), any()))
//                .thenReturn(ResponseEntity.ok().body("reprovado"));
//
//        ResponseEntity<?> response = pontoController.reprovarPonto(1, request);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("reprovado", response.getBody());
//    }
//
//    // reprovarPonto - Exception
//    @Test
//    void testReprovarPonto_Exception() {
//        when(pontoService.reprovarPonto(anyInt(), any()))
//                .thenThrow(new RuntimeException("Erro"));
//
//        try (MockedStatic<ExceptionHandlerUtil> staticUtil = mockStatic(ExceptionHandlerUtil.class)) {
//            staticUtil.when(() -> ExceptionHandlerUtil.tratarExcecao(any(), any()))
//                    .thenReturn(ResponseEntity.status(500).body("error"));
//
//            ResponseEntity<?> response = pontoController.reprovarPonto(1, request);
//
//            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//            staticUtil.verify(() -> ExceptionHandlerUtil.tratarExcecao(any(), any()), times(1));
//        }
//    }


    void startRegistroCompleto() {
        registroCompleto = new RegistroCompletoDto(
                1,
                "",
                "",
                Time.valueOf("08:00:00"),
                Time.valueOf("08:00:00"),
                Time.valueOf("12:00:00"),
                Time.valueOf("13:00:00"),
                Time.valueOf("17:00:00"),
                null,
                null,
                Status.PENDENTE
        );
    }
}