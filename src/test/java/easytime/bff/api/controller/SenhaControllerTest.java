package easytime.bff.api.controller;

import easytime.bff.api.dto.senha.CodigoValidacao;
import easytime.bff.api.dto.senha.EmailRequest;
import easytime.bff.api.service.SenhaService;
import easytime.bff.api.util.ExceptionHandlerUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SenhaControllerTest {

    private SenhaController controller;
    private SenhaService service;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() throws Exception {
        service = mock(SenhaService.class);
        request = mock(HttpServletRequest.class);
        controller = new SenhaController();

        // Inject mock service
        Field serviceField = SenhaController.class.getDeclaredField("service");
        serviceField.setAccessible(true);
        serviceField.set(controller, service);
    }

    private Logger getLogger() throws Exception {
        Field loggerField = SenhaController.class.getDeclaredField("LOGGER");
        loggerField.setAccessible(true);
        return (Logger) loggerField.get(null);
    }

    @Test
    void redefinirSenha_success() {
        CodigoValidacao dto = mock(CodigoValidacao.class);
        when(dto.email()).thenReturn("user@email.com");
        when(service.redefinirSenha(any()))
                .thenReturn(ResponseEntity.ok("Senha redefinida"));

        var response = controller.redefinirSenha(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Senha redefinida", response.getBody());
    }

    @Test
    void redefinirSenha_callsExceptionHandlerOnException() throws Exception {
        CodigoValidacao dto = mock(CodigoValidacao.class);
        when(dto.email()).thenReturn("user@email.com");
        RuntimeException ex = new RuntimeException("fail");
        when(service.redefinirSenha(any())).thenThrow(ex);

        Logger logger = getLogger();

        try (MockedStatic<ExceptionHandlerUtil> staticUtil = mockStatic(ExceptionHandlerUtil.class)) {
            staticUtil.when(() -> ExceptionHandlerUtil.tratarExcecao(ex, logger))
                    .thenReturn(ResponseEntity.status(500).body("error"));

            var response = controller.redefinirSenha(dto);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals("error", response.getBody());
            staticUtil.verify(() -> ExceptionHandlerUtil.tratarExcecao(ex, logger), times(1));
        }
    }

    @Test
    void enviarCodigo_success() {
        EmailRequest dto = mock(EmailRequest.class);
        when(dto.email()).thenReturn("user@email.com");
        when(service.enviarCodigo(any()))
                .thenReturn(ResponseEntity.ok("Código enviado"));

        var response = controller.enviarCodigo(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Código enviado", response.getBody());
    }

    @Test
    void enviarCodigo_callsExceptionHandlerOnException() throws Exception {
        EmailRequest dto = mock(EmailRequest.class);
        when(dto.email()).thenReturn("user@email.com");
        RuntimeException ex = new RuntimeException("fail");
        when(service.enviarCodigo(any())).thenThrow(ex);

        Logger logger = getLogger();

        try (MockedStatic<ExceptionHandlerUtil> staticUtil = mockStatic(ExceptionHandlerUtil.class)) {
            staticUtil.when(() -> ExceptionHandlerUtil.tratarExcecao(ex, logger))
                    .thenReturn(ResponseEntity.status(500).body("error"));

            var response = controller.enviarCodigo(dto);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals("error", response.getBody());
            staticUtil.verify(() -> ExceptionHandlerUtil.tratarExcecao(ex, logger), times(1));
        }
    }
}