package easytime.bff.api.service;

import easytime.bff.api.dto.usuario.UsuarioDto;
import easytime.bff.api.dto.usuario.UsuarioRetornoDto;
import easytime.bff.api.util.HttpHeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.GET;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpHeaderUtil httpHeaderUtil;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void criarUsuarioComSucesso() throws NoSuchFieldException, IllegalAccessException {
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.singletonList("Authorization")));
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(httpHeaderUtil.copyHeaders(request)).thenReturn(headers);

        UsuarioRetornoDto mockResponse = Mockito.mock(UsuarioRetornoDto.class);
        HttpEntity<UsuarioDto> entity = new HttpEntity<>(Mockito.mock(UsuarioDto.class), headers);

        when(restTemplate.exchange("http://localhost:8080/users/create", HttpMethod.PUT, entity, Object.class)).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        Field field = UsuarioService.class.getDeclaredField("restTemplate");
        field.setAccessible(true);
        field.set(service, restTemplate);

        service.criarUsuario(Mockito.mock(UsuarioDto.class), request);
    }

    @Test
    @DisplayName("Deve retornar erro ao criar usuário")
    void criarUsuarioErro() throws NoSuchFieldException, IllegalAccessException {
        String url = "http://localhost:8080/users/create";
        UsuarioDto usuarioDto = mock(UsuarioDto.class);

        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.singletonList("Authorization")));
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(httpHeaderUtil.copyHeaders(request)).thenReturn(headers);

        when(restTemplate.exchange(eq(url), eq(HttpMethod.PUT), any(HttpEntity.class), eq(Object.class)))
                .thenThrow(new RuntimeException("Erro ao criar usuário"));

        Field field = UsuarioService.class.getDeclaredField("restTemplate");
        field.setAccessible(true);
        field.set(service, restTemplate);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.criarUsuario(usuarioDto, request));
        assertEquals("Erro ao criar usuário", exception.getMessage());
    }

    @Test
    @DisplayName("Deve listar usuários com sucesso")
    void listarUsuariosComSucesso() throws NoSuchFieldException, IllegalAccessException {
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.singletonList("Authorization")));
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(httpHeaderUtil.copyHeaders(request)).thenReturn(headers);

        List<UsuarioRetornoDto> usuarios = List.of(mock(UsuarioRetornoDto.class));
        HttpEntity<UsuarioDto> entity = new HttpEntity<>(headers);

        when(restTemplate.exchange("http://localhost:8080/users/list", GET, entity, ParameterizedTypeReference.class)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        Field field = UsuarioService.class.getDeclaredField("restTemplate");
        field.setAccessible(true);
        field.set(service, restTemplate);

        service.listarUsuarios(request);
    }

    @Test
    @DisplayName("Deve listar usuário por ID com sucesso")
    void listarUsuarioPorIdComSucesso() throws NoSuchFieldException, IllegalAccessException {
        int id = 1;
        String url = "http://localhost:8080/users/getById/" + id;
        UsuarioRetornoDto usuario = mock(UsuarioRetornoDto.class);
        ResponseEntity<UsuarioRetornoDto> expectedResponse = new ResponseEntity<>(usuario, HttpStatus.OK);

        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.singletonList("Authorization")));
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(httpHeaderUtil.copyHeaders(request)).thenReturn(headers);

        when(restTemplate.exchange(eq(url), eq(GET), any(HttpEntity.class), eq(UsuarioRetornoDto.class)))
                .thenReturn(expectedResponse);

        Field field = UsuarioService.class.getDeclaredField("restTemplate");
        field.setAccessible(true);
        field.set(service, restTemplate);

        ResponseEntity<UsuarioRetornoDto> response = service.listarUsuarioPorId(id, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
    }

    @Test
    @DisplayName("Deve retornar erro ao listar usuário por ID inexistente")
    void listarUsuarioPorIdInexistente() throws NoSuchFieldException, IllegalAccessException {
        int id = 999; // ID inexistente
        String url = "http://localhost:8080/users/getById/" + id;

        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.singletonList("Authorization")));
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(httpHeaderUtil.copyHeaders(request)).thenReturn(headers);

        when(restTemplate.exchange(eq(url), eq(GET), any(HttpEntity.class), eq(UsuarioRetornoDto.class)))
                .thenThrow(new RuntimeException("Usuário não encontrado"));

        Field field = UsuarioService.class.getDeclaredField("restTemplate");
        field.setAccessible(true);
        field.set(service, restTemplate);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.listarUsuarioPorId(id, request));
        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void deletarUsuarioComSucesso() throws NoSuchFieldException, IllegalAccessException {
        int id = 1;
        String url = "http://localhost:8080/users/delete/" + id;
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Usuário deletado com sucesso", HttpStatus.OK);

        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.singletonList("Authorization")));
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(httpHeaderUtil.copyHeaders(request)).thenReturn(headers);

        when(restTemplate.exchange(eq("http://localhost:8080/users/delete/" + id), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(String.class)))
                .thenReturn(expectedResponse);

        Field field = UsuarioService.class.getDeclaredField("restTemplate");
        field.setAccessible(true);
        field.set(service, restTemplate);

        ResponseEntity<String> response = service.deletarUsuario(id, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuário deletado com sucesso", response.getBody());
        verify(restTemplate, times(1)).exchange(eq(url), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(String.class));
    }
}