package easytime.bff.api.controller;

import easytime.bff.api.dto.usuario.UsuarioDto;
import easytime.bff.api.dto.usuario.UsuarioRetornoDto;
import easytime.bff.api.service.UsuarioService;
import easytime.bff.api.util.ExceptionHandlerUtil;
import easytime.bff.api.validacoes.cadastro.ValidacoesCadastro;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    private UsuarioController controller;
    private UsuarioService service;
    private List<ValidacoesCadastro> validacoes;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() throws Exception {
        service = mock(UsuarioService.class);
        validacoes = spy(new ArrayList<>());
        request = mock(HttpServletRequest.class);
        controller = new UsuarioController();

        Field serviceField = UsuarioController.class.getDeclaredField("service");
        serviceField.setAccessible(true);
        serviceField.set(controller, service);

        Field validacoesField = UsuarioController.class.getDeclaredField("validacoes");
        validacoesField.setAccessible(true);
        validacoesField.set(controller, validacoes);
    }

    private Logger getLogger() throws Exception {
        Field loggerField = UsuarioController.class.getDeclaredField("LOGGER");
        loggerField.setAccessible(true);
        return (Logger) loggerField.get(null);
    }

    @Test
    void criarUsuario_success() {
        UsuarioDto dto = mock(UsuarioDto.class);
        when(dto.nome()).thenReturn("Nome");
        when(dto.email()).thenReturn("email");
        when(dto.login()).thenReturn("login");
        when(dto.password()).thenReturn("password");
        when(dto.sector()).thenReturn("sector");
        when(dto.jobTitle()).thenReturn("jobTitle");
        when(dto.role()).thenReturn("role");
        doNothing().when(validacoes).forEach(any());
        when(service.criarUsuario(any(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(dto));

        var response = controller.criarUsuario(dto, request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(service, times(1)).criarUsuario(dto, request);
        verify(validacoes, times(1)).forEach(any());
    }

    @Test
    void criarUsuario_callsExceptionHandlerOnException() throws Exception {
        UsuarioDto dto = mock(UsuarioDto.class);
        when(dto.nome()).thenReturn("Nome");
        when(dto.email()).thenReturn("email");
        when(dto.login()).thenReturn("login");
        when(dto.password()).thenReturn("password");
        when(dto.sector()).thenReturn("sector");
        when(dto.jobTitle()).thenReturn("jobTitle");
        when(dto.role()).thenReturn("role");
        doThrow(new RuntimeException("fail")).when(validacoes).forEach(any());

        Logger logger = getLogger();

        try (MockedStatic<ExceptionHandlerUtil> staticUtil = mockStatic(ExceptionHandlerUtil.class)) {
            staticUtil.when(() -> ExceptionHandlerUtil.tratarExcecao(any(Exception.class), eq(logger)))
                    .thenReturn(ResponseEntity.status(500).body("error"));

            var response = controller.criarUsuario(dto, request);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals("error", response.getBody());
            staticUtil.verify(() -> ExceptionHandlerUtil.tratarExcecao(any(Exception.class), eq(logger)), times(1));
        }
    }

    @Test
    void listarUsuarios_success() {
        List<UsuarioRetornoDto> usuarios = List.of(mock(UsuarioRetornoDto.class));
        when(service.listarUsuarios(request)).thenReturn(ResponseEntity.ok(usuarios));

        var response = controller.listarUsuarios(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarios, response.getBody());
        verify(service, times(1)).listarUsuarios(request);
    }

    @Test
    void listarUsuarios_callsExceptionHandlerOnException() throws Exception {
        when(service.listarUsuarios(request)).thenThrow(new RuntimeException("fail"));
        Logger logger = getLogger();

        try (MockedStatic<ExceptionHandlerUtil> staticUtil = mockStatic(ExceptionHandlerUtil.class)) {
            staticUtil.when(() -> ExceptionHandlerUtil.tratarExcecao(any(Exception.class), eq(logger)))
                    .thenReturn(ResponseEntity.status(500).body("error"));

            var response = controller.listarUsuarios(request);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals("error", response.getBody());
            staticUtil.verify(() -> ExceptionHandlerUtil.tratarExcecao(any(Exception.class), eq(logger)), times(1));
        }
    }

    @Test
    void listarUsuarioPorId_success() {
        int id = 1;
        UsuarioRetornoDto usuario = mock(UsuarioRetornoDto.class);
        when(service.listarUsuarioPorId(id, request)).thenReturn(ResponseEntity.ok(usuario));

        var response = controller.listarUsuarios(id, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
        verify(service, times(1)).listarUsuarioPorId(id, request);
    }

    @Test
    void listarUsuarioPorId_callsExceptionHandlerOnException() throws Exception {
        int id = 1;
        when(service.listarUsuarioPorId(id, request)).thenThrow(new RuntimeException("fail"));
        Logger logger = getLogger();

        try (MockedStatic<ExceptionHandlerUtil> staticUtil = mockStatic(ExceptionHandlerUtil.class)) {
            staticUtil.when(() -> ExceptionHandlerUtil.tratarExcecao(any(Exception.class), eq(logger)))
                    .thenReturn(ResponseEntity.status(500).body("error"));

            var response = controller.listarUsuarios(id, request);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals("error", response.getBody());
            staticUtil.verify(() -> ExceptionHandlerUtil.tratarExcecao(any(Exception.class), eq(logger)), times(1));
        }
    }

    @Test
    void deletarUsuario_success() {
        int id = 1;
        when(service.deletarUsuario(id, request)).thenReturn(ResponseEntity.ok("Usuário deletado"));

        var response = controller.deletarUsuario(id, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuário deletado", response.getBody());
        verify(service, times(1)).deletarUsuario(id, request);
    }

    @Test
    void deletarUsuario_callsExceptionHandlerOnException() throws Exception {
        int id = 1;
        when(service.deletarUsuario(id, request)).thenThrow(new RuntimeException("fail"));
        Logger logger = getLogger();

        try (MockedStatic<ExceptionHandlerUtil> staticUtil = mockStatic(ExceptionHandlerUtil.class)) {
            staticUtil.when(() -> ExceptionHandlerUtil.tratarExcecao(any(Exception.class), eq(logger)))
                    .thenReturn(ResponseEntity.status(500).body("error"));

            var response = controller.deletarUsuario(id, request);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals("error", response.getBody());
            staticUtil.verify(() -> ExceptionHandlerUtil.tratarExcecao(any(Exception.class), eq(logger)), times(1));
        }
    }
}