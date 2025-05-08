package easytime.bff.api.controller;

import easytime.bff.api.dto.CodigoValidacao;
import easytime.bff.api.dto.EmailRequest;
import easytime.bff.api.dto.UsuarioDto;
import easytime.bff.api.dto.UsuarioRetornoDto;
import easytime.bff.api.infra.security.SecurityFilter;
import easytime.bff.api.infra.security.TokenService;
import easytime.bff.api.service.SenhaService;
import easytime.bff.api.validacoes.cadastro.ValidacoesCadastro;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
class SenhaControllerTest {

    @InjectMocks
    private SenhaController controller;

    @Autowired
    private MockMvc mvc;

    @Mock
    private SenhaService service;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar 200 ao redefinir senha com sucesso")
    void redefinirSenhaComSucesso() {
        CodigoValidacao dto = mock(CodigoValidacao.class);
        when(dto.senha()).thenReturn("123456");
        when(dto.email()).thenReturn("email@email.com");
        when(dto.code()).thenReturn("123456");

        when(service.redefinirSenha(dto, request)).thenReturn(ResponseEntity.status(HttpStatus.OK).body("Senha redefinida com sucesso"));

        ResponseEntity<?> response = controller.redefinirSenha(dto, request);

        assertEquals(200, response.getStatusCode().value());
        verify(service, times(1)).redefinirSenha(dto, request);
    }

    @Test
    @DisplayName("Deve retornar 400 ao redefinir senha com campo vazio")
    void redefinirSenhaComCampoVazio() {
        CodigoValidacao dto = mock(CodigoValidacao.class);
        when(dto.senha()).thenReturn(null);
        when(dto.email()).thenReturn("email@email.com");
        when(dto.code()).thenReturn("123456");

        ResponseEntity<?> response = controller.redefinirSenha(dto, request);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve retornar 403 com usuario não autorizado")
    void redefinirSenhaNaoAutorizado() throws Exception {
        String json = "";

        var response = mvc.perform(
                put("/senha/redefinir")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(403, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 200 ao enviar codigo com sucesso")
    void envairCodigoComSucesso() {
        EmailRequest dto = mock(EmailRequest.class);
        when(dto.email()).thenReturn("email@email.com");

        when(service.enviarCodigo(dto, request)).thenReturn(ResponseEntity.status(HttpStatus.OK).body("Código enviado com sucesso"));

        ResponseEntity<?> response = controller.enviarCodigo(dto, request);

        assertEquals(200, response.getStatusCode().value());
        verify(service, times(1)).enviarCodigo(dto, request);
    }

    @Test
    @DisplayName("Deve retornar 400 ao enviar codigo com campo vazio")
    void enviarCodigoComCampoVazio() {
        EmailRequest dto = mock(EmailRequest.class);
        when(dto.email()).thenReturn(null);

        ResponseEntity<?> response = controller.enviarCodigo(dto, request);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve retornar 403 com usuario não autorizado")
    void enviarCodigoNaoAutorizado() throws Exception {
        String json = "";

        var response = mvc.perform(
                put("/senha/enviar-codigo")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(403, response.getStatus());
    }
}