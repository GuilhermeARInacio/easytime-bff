package easytime.bff.api.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExceptionHandlerUtilTest {

    private final Logger logger = mock(Logger.class);

    @Test
    void tratarExcecao_deveTratarHttpClientErrorException401ComCodigo() {
        HttpClientErrorException ex = mock(HttpClientErrorException.class);
        when(ex.getStatusCode()).thenReturn(HttpStatus.UNAUTHORIZED);
        when(ex.getMessage()).thenReturn("Erro com código");
        when(ex.getResponseBodyAsString()).thenReturn("body");
        ResponseEntity<?> response = ExceptionHandlerUtil.tratarExcecao(ex, logger);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Tente enviar um novo código, ocorreu um erro pois o código está inválido.", response.getBody());
    }

    @Test
    void tratarExcecao_deveTratarHttpClientErrorException401SemCodigo() {
        HttpClientErrorException ex = mock(HttpClientErrorException.class);
        when(ex.getStatusCode()).thenReturn(HttpStatus.UNAUTHORIZED);
        when(ex.getMessage()).thenReturn("Usuário não autorizado");
        when(ex.getResponseBodyAsString()).thenReturn("body");
        ResponseEntity<?> response = ExceptionHandlerUtil.tratarExcecao(ex, logger);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Usuário não autorizado.", response.getBody());
    }

    @Test
    void tratarExcecao_deveTratarHttpClientErrorException404() {
        HttpClientErrorException ex = mock(HttpClientErrorException.class);
        when(ex.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
        when(ex.getMessage()).thenReturn("Not found");
        when(ex.getResponseBodyAsString()).thenReturn("body");
        ResponseEntity<?> response = ExceptionHandlerUtil.tratarExcecao(ex, logger);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Serviço não encontrado.", response.getBody());
    }

    @Test
    void tratarExcecao_deveTratarHttpClientErrorException4xxDefault() {
        HttpClientErrorException ex = mock(HttpClientErrorException.class);
        when(ex.getStatusCode()).thenReturn(HttpStatus.FORBIDDEN);
        when(ex.getMessage()).thenReturn("Forbidden");
        when(ex.getResponseBodyAsString()).thenReturn("forbidden body");
        ResponseEntity<?> response = ExceptionHandlerUtil.tratarExcecao(ex, logger);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("forbidden body", response.getBody());
    }

    @Test
    void tratarExcecao_deveTratarHttpClientErrorException5xx() {
        HttpClientErrorException ex = mock(HttpClientErrorException.class);
        when(ex.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        when(ex.getMessage()).thenReturn("Erro interno");
        when(ex.getResponseBodyAsString()).thenReturn("server error");
        ResponseEntity<?> response = ExceptionHandlerUtil.tratarExcecao(ex, logger);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro interno do servidor.", response.getBody());
    }

    @Test
    void tratarExcecao_deveTratarIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Campo inválido");
        ResponseEntity<?> response = ExceptionHandlerUtil.tratarExcecao(ex, logger);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Campo inválido", response.getBody());
    }

    @Test
    void tratarExcecao_deveTratarNullPointerException() {
        NullPointerException ex = new NullPointerException("Nulo");
        ResponseEntity<?> response = ExceptionHandlerUtil.tratarExcecao(ex, logger);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Nulo", response.getBody());
    }

    @Test
    void tratarExcecao_deveTratarHttpMessageNotReadableException() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Mensagem não legível");
        ResponseEntity<?> response = ExceptionHandlerUtil.tratarExcecao(ex, logger);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Mensagem não legível", response.getBody());
    }

    @Test
    void tratarExcecao_deveTratarRuntimeExceptionCom404EUsuario() {
        RuntimeException ex = new RuntimeException("Erro 404 Usuário não encontrado");
        ResponseEntity<?> response = ExceptionHandlerUtil.tratarExcecao(ex, logger);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuário não encontrado.", response.getBody());
    }

    @Test
    void tratarExcecao_deveTratarRuntimeExceptionGenerico() {
        RuntimeException ex = new RuntimeException("Erro genérico");
        ResponseEntity<?> response = ExceptionHandlerUtil.tratarExcecao(ex, logger);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro genérico", response.getBody());
    }

    @Test
    void tratarExcecao_deveTratarExceptionGenerico() {
        Exception ex = new Exception("Erro desconhecido");
        ResponseEntity<?> response = ExceptionHandlerUtil.tratarExcecao(ex, logger);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro interno do servidor.", response.getBody());
    }

    @Test
    void tratarHttpClientErrorException_deveRetornarMensagemParaStatusNao4xxNem5xx() {
        HttpClientErrorException ex = mock(HttpClientErrorException.class);
        when(ex.getStatusCode()).thenReturn(HttpStatus.MOVED_PERMANENTLY); // 301
        when(ex.getMessage()).thenReturn("Redirecionamento");
        Logger logger = mock(Logger.class);

        ResponseEntity<?> response = ExceptionHandlerUtil
                .tratarExcecao(ex, logger);

        assertEquals(HttpStatus.MOVED_PERMANENTLY, response.getStatusCode());
        assertEquals("Redirecionamento", response.getBody());
    }
}