package easytime.bff.api.service;

import easytime.bff.api.dto.usuario.UsuarioDto;
import easytime.bff.api.dto.usuario.UsuarioRetornoDto;
import easytime.bff.api.util.HttpHeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private HttpServletRequest request;

    private HttpHeaders headers;
    private AutoCloseable mocks;
    private MockedStatic<HttpHeaderUtil> staticHttpHeaderUtil;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer token");
        staticHttpHeaderUtil = mockStatic(HttpHeaderUtil.class);
        staticHttpHeaderUtil.when(() -> HttpHeaderUtil.copyHeaders(request)).thenReturn(headers);

        ReflectionTestUtils.setField(usuarioService, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(usuarioService, "urlSrv", "http://localhost:8080/");
    }

    @AfterEach
    void tearDown() throws Exception {
        staticHttpHeaderUtil.close();
        mocks.close();
    }

    @Test
    void criarUsuario_shouldCallRestTemplate() {
        UsuarioDto dto = mock(UsuarioDto.class);
        ResponseEntity<Object> expected = ResponseEntity.ok("created");

        when(restTemplate.exchange(
                eq("http://localhost:8080/users/create"),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(Object.class)
        )).thenReturn(expected);

        ResponseEntity<Object> result = usuarioService.criarUsuario(dto, request);

        assertEquals(expected, result);
    }

    @Test
    void listarUsuarios_shouldCallRestTemplate() {
        List<UsuarioRetornoDto> usuarios = Collections.emptyList();
        ResponseEntity<List<UsuarioRetornoDto>> expected = ResponseEntity.ok(usuarios);

        when(restTemplate.exchange(
                eq("http://localhost:8080/users/list"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<List<UsuarioRetornoDto>>>any()
        )).thenReturn(expected);

        ResponseEntity<List<UsuarioRetornoDto>> result = usuarioService.listarUsuarios(request);

        assertEquals(expected, result);
    }

    @Test
    void listarUsuarioPorId_shouldCallRestTemplate() {
        int id = 1;
        UsuarioRetornoDto usuario = mock(UsuarioRetornoDto.class);
        ResponseEntity<UsuarioRetornoDto> expected = ResponseEntity.ok(usuario);

        when(restTemplate.exchange(
                eq("http://localhost:8080/users/getById/1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(UsuarioRetornoDto.class)
        )).thenReturn(expected);

        ResponseEntity<UsuarioRetornoDto> result = usuarioService.listarUsuarioPorId(id, request);

        assertEquals(expected, result);
    }

    @Test
    void deletarUsuario_shouldCallRestTemplate() {
        int id = 1;
        ResponseEntity<String> expected = ResponseEntity.ok("deleted");

        when(restTemplate.exchange(
                eq("http://localhost:8080/users/delete/1"),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(expected);

        ResponseEntity<String> result = usuarioService.deletarUsuario(id, request);

        assertEquals(expected, result);
    }
}