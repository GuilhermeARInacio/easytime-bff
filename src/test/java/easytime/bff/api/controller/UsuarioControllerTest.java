package easytime.bff.api.controller;

import easytime.bff.api.dto.UsuarioDto;
import easytime.bff.api.dto.UsuarioRetornoDto;
import easytime.bff.api.infra.security.SecurityFilter;
import easytime.bff.api.infra.security.TokenService;
import easytime.bff.api.service.UsuarioService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.config.http.MatcherType.mvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController controller;

    @Autowired
    private MockMvc mvc;

    @Mock
    private UsuarioService service;

    @Mock
    private List<ValidacoesCadastro> validacoes;

    @Mock
    private HttpServletRequest request;

    @Mock
    private SecurityFilter securityFilter;

    @Mock
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validacoes = new ArrayList<>();
    }

    @Test
    @DisplayName("Deve retornar 201 ao criar usuário com sucesso")
    void criarUsuarioComSucesso() {
        UsuarioDto dto = mock(UsuarioDto.class);
        when(dto.nome()).thenReturn("Nome");
        when(dto.email()).thenReturn("email");
        when(dto.login()).thenReturn("login");
        when(dto.password()).thenReturn("password");
        when(dto.sector()).thenReturn("sector");
        when(dto.jobTitle()).thenReturn("jobTitle");
        when(dto.role()).thenReturn("role");
        when(service.criarUsuario(dto, request)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(dto));

        ResponseEntity<?> response = controller.criarUsuario(dto, request);

        assertEquals(201, response.getStatusCode().value());
        verify(service, times(1)).criarUsuario(dto, request);
    }

    @Test
    @DisplayName("Deve retornar 400 ao criar usuário com campos inválidos")
    void criarUsuarioComCamposInvalidos() {
        UsuarioDto dto = mock(UsuarioDto.class);
        when(dto.nome()).thenReturn(null);

        ResponseEntity<?> response = controller.criarUsuario(dto, request);

        assertEquals(400, response.getStatusCode().value());
        verify(service, never()).criarUsuario(dto, request);
    }

    @Test
    @DisplayName("Deve retornar 403 ao criar usuário sem autorização")
    void criarUsuarioNaoAutorizado() throws Exception {
        String json = "";

        var response = mvc.perform(
                put("/users/create")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(403, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 200 ao listar usuários com sucesso")
    void listarUsuariosComSucesso() {
        List<UsuarioRetornoDto> usuarios = List.of(mock(UsuarioRetornoDto.class));
        when(service.listarUsuarios(request)).thenReturn(ResponseEntity.ok(usuarios));

        ResponseEntity<?> response = controller.listarUsuarios(request);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve retornar 200 ao buscar usuário por ID com sucesso")
    void listarUsuarioPorIdComSucesso() {
        int id = 1;
        UsuarioRetornoDto usuario = mock(UsuarioRetornoDto.class);
        when(service.listarUsuarioPorId(id, request)).thenReturn(ResponseEntity.ok(usuario));

        ResponseEntity<?> response = controller.listarUsuarios(id, request);

        assertEquals(200, response.getStatusCode().value());
        verify(service, times(1)).listarUsuarioPorId(id, request);
    }

    @Test
    @DisplayName("Deve retornar 400 ao buscar usuário por ID inexistente")
    void listarUsuarioPorIdInexistente() {
        int id = 999;
        when(service.listarUsuarioPorId(id, request)).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<?> response = controller.listarUsuarios(id, request);

        assertEquals(400, response.getStatusCode().value());
        verify(service, times(1)).listarUsuarioPorId(id, request);
    }

    @Test
    @DisplayName("Deve retornar 200 ao deletar usuário com sucesso")
    void deletarUsuarioComSucesso() {
        int id = 1;
        when(service.deletarUsuario(id, request)).thenReturn(ResponseEntity.ok("Usuário deletado com sucesso"));

        ResponseEntity<?> response = controller.deletarUsuario(id, request);

        assertEquals(200, response.getStatusCode().value());
        verify(service, times(1)).deletarUsuario(id, request);
    }
}