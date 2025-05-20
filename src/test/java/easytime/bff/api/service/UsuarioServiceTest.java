package easytime.bff.api.service;

import easytime.bff.api.dto.usuario.LoginDto;
import easytime.bff.api.dto.usuario.UsuarioDto;
import easytime.bff.api.dto.usuario.UsuarioRetornoDto;
import easytime.bff.api.util.HttpHeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
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

    private MockedStatic<HttpHeaderUtil> mockedStaticHttpHeaderUtil;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer token");

        // Mock HttpHeaderUtil.copyHeaders to return the headers
        mockedStaticHttpHeaderUtil = mockStatic(HttpHeaderUtil.class);
        mockedStaticHttpHeaderUtil.when(() -> HttpHeaderUtil.copyHeaders(request)).thenReturn(headers);
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.singletonList("Authorization")));
        when(request.getHeader("Authorization")).thenReturn("Bearer token");

        Field field = UsuarioService.class.getDeclaredField("restTemplate");
        field.setAccessible(true);
        field.set(service, restTemplate);
    }

    @AfterEach
    void tearDown() {
        if (mockedStaticHttpHeaderUtil != null) {
            mockedStaticHttpHeaderUtil.close();
        }
    }

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void criarUsuarioComSucesso() {
        UsuarioRetornoDto mockResponse = Mockito.mock(UsuarioRetornoDto.class);
        HttpEntity<UsuarioDto> entity = new HttpEntity<>(Mockito.mock(UsuarioDto.class), headers);

        when(httpHeaderUtil.copyHeaders(request)).thenReturn(headers);
        when(restTemplate.exchange("http://localhost:8080/users/create", HttpMethod.PUT, entity, Object.class))
                .thenReturn(ResponseEntity.ok(mockResponse));

        service.criarUsuario(Mockito.mock(UsuarioDto.class), request);
    }

    @Test
    @DisplayName("Deve listar usuários com sucesso")
    void listarUsuariosComSucesso() {
        List<UsuarioRetornoDto> usuarios = List.of(mock(UsuarioRetornoDto.class));
        HttpEntity<UsuarioDto> entity = new HttpEntity<>(headers);

        when(httpHeaderUtil.copyHeaders(request)).thenReturn(headers);
        when(restTemplate.exchange("http://localhost:8080/users/list", HttpMethod.GET, entity, Object.class))
                .thenReturn(ResponseEntity.ok(usuarios));

        service.listarUsuarios(request);
    }

    @Test
    @DisplayName("Deve listar usuário por ID com sucesso")
    void listarUsuarioPorIdComSucesso() {
        Integer id = 1;
        String url = "http://localhost:8080/users/getById/" + id;

        HttpEntity<UsuarioDto> entity = new HttpEntity<>(headers);

        when(httpHeaderUtil.copyHeaders(request)).thenReturn(headers);
        when(restTemplate.exchange(url, HttpMethod.GET, entity, UsuarioRetornoDto.class))
                .thenReturn(ResponseEntity.ok(mock(UsuarioRetornoDto.class)));

        ResponseEntity<UsuarioRetornoDto> response = service.listarUsuarioPorId(id, request);
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void deletarUsuarioComSucesso() throws NoSuchFieldException, IllegalAccessException {
        int id = 1;
        String url = "http://localhost:8080/users/delete/" + id;
        HttpEntity<UsuarioDto> entity = new HttpEntity<>(headers);

        when(httpHeaderUtil.copyHeaders(request)).thenReturn(headers);
        when(restTemplate.exchange(url, HttpMethod.DELETE, entity, Object.class))
                .thenReturn(ResponseEntity.ok(""));

        ResponseEntity<String> response = service.deletarUsuario(id, request);
    }
}