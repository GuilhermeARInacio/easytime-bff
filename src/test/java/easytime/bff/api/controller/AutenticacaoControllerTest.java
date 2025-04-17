package easytime.bff.api.controller;

import easytime.bff.api.dto.DadosAutenticacao;
import easytime.bff.api.service.AutenticacaoService;
import easytime.bff.api.validacoes.login.ValidacoesLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AutenticacaoControllerTest {

    @InjectMocks
    private AutenticacaoController controller;

    @Autowired
    private MockMvc mvc;

    @Mock
    private List<ValidacoesLogin> validacoes;

    @Mock
    private AutenticacaoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar 200 para autenticação bem-sucedida")
    void codigo200paraSucesso() {
        DadosAutenticacao dto = Mockito.mock(DadosAutenticacao.class);
        when(dto.senha()).thenReturn("senha");
        when(dto.usuario()).thenReturn("usuario");
        when(service.autenticar(dto)).thenReturn("token");

        ResponseEntity<?> response = controller.autenticar(dto);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve retornar 400 para campos em branco")
    void codigo400paraCamposEmBranco() {
        DadosAutenticacao dto = Mockito.mock(DadosAutenticacao.class);
        when(dto.senha()).thenReturn(null);
        when(dto.usuario()).thenReturn("usuario");

        ResponseEntity<?> response = controller.autenticar(dto);
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve retornar 400 para corpo da requisição em branco")
    void codigo400paraCorpoEmBranco() throws Exception {
        String json = "";

        var response = mvc.perform(
                post("/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 401 para usuário não autorizado")
    void codigo401paraUsuarioNaoAutorizado() {
        DadosAutenticacao dto = Mockito.mock(DadosAutenticacao.class);
        when(dto.senha()).thenReturn("senha");
        when(dto.usuario()).thenReturn("usuario");
        when(service.autenticar(dto)).thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

        ResponseEntity<?> response = controller.autenticar(dto);
        assertEquals(401, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve retornar 500 para erro interno do servidor")
    void codigo404paraErroInterno() {
        DadosAutenticacao dto = Mockito.mock(DadosAutenticacao.class);
        when(dto.senha()).thenReturn("senha");
        when(dto.usuario()).thenReturn("usuario");
        when(service.autenticar(dto)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        ResponseEntity<?> response = controller.autenticar(dto);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve retornar 500 para erro interno do servidor")
    void codigo500paraErroInterno() {
        DadosAutenticacao dto = Mockito.mock(DadosAutenticacao.class);
        when(dto.senha()).thenReturn("senha");
        when(dto.usuario()).thenReturn("usuario");
        when(service.autenticar(dto)).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<?> response = controller.autenticar(dto);
        assertEquals(500, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve retornar 400 para formato de senha ou usuário inválido")
    void codigo400paraFormatoInvalido() throws Exception {
        DadosAutenticacao dto = Mockito.mock(DadosAutenticacao.class);
        when(dto.senha()).thenReturn("senha");
        when(dto.usuario()).thenReturn("usuario");
        when(service.autenticar(dto)).thenThrow(new IllegalArgumentException());

        ResponseEntity<?> response = controller.autenticar(dto);
        assertEquals(400, response.getStatusCode().value());
    }
}