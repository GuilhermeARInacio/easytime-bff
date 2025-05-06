package easytime.bff.api.service;

import easytime.bff.api.dto.UsuarioDto;
import easytime.bff.api.dto.UsuarioRetornoDto;
import easytime.bff.api.util.HttpHeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpHeaderUtil httpHeaderUtil;

    @Mock
    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void criarUsuarioComSucesso() {
        UsuarioDto dto = mock(UsuarioDto.class);
        RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
        String url = "http://localhost:8080/users/create";
        HttpHeaders headers = Mockito.mock(HttpHeaders.class);
        Enumeration<String> headerNames = Collections.enumeration(List.of("Authorization", "header2"));

        HttpEntity<UsuarioDto> entity = Mockito.mock(HttpEntity.class);

        when(request.getHeaderNames()).thenReturn(headerNames);
        when(request.getHeader("Authorization")).thenReturn("token");
        when(httpHeaderUtil.copyHeaders(request)).thenReturn(headers);
        when(restTemplateMock.exchange(url, PUT.asHttpMethod(), entity, Object.class)).thenReturn(ResponseEntity.ok(dto));

        ResponseEntity<Object> response = service.criarUsuario(dto, request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}

//    @Test
//    @DisplayName("Deve listar usuários com sucesso")
//    void listarUsuariosComSucesso() {
//        String url = "http://localhost:8080/users/list";
//        List<UsuarioRetornoDto> usuarios = List.of(mock(UsuarioRetornoDto.class));
//        ResponseEntity<List<UsuarioRetornoDto>> expectedResponse = new ResponseEntity<>(usuarios, HttpStatus.OK);
//
//        when(restTemplate.exchange(eq(url), eq(GET), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
//                .thenReturn(expectedResponse);
//
//        ResponseEntity<List<UsuarioRetornoDto>> response = service.listarUsuarios(request);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(usuarios, response.getBody());
//        verify(restTemplate, times(1)).exchange(eq(url), eq(GET), any(HttpEntity.class), any(ParameterizedTypeReference.class));
//    }
//
//    @Test
//    @DisplayName("Deve listar usuário por ID com sucesso")
//    void listarUsuarioPorIdComSucesso() {
//        int id = 1;
//        String url = "http://localhost:8080/users/getById/" + id;
//        UsuarioRetornoDto usuario = mock(UsuarioRetornoDto.class);
//        ResponseEntity<UsuarioRetornoDto> expectedResponse = new ResponseEntity<>(usuario, HttpStatus.OK);
//
//        when(restTemplate.exchange(url, GET, new HttpEntity<>(headers), UsuarioRetornoDto.class))
//                .thenReturn(expectedResponse);
//
//        ResponseEntity<UsuarioRetornoDto> response = service.listarUsuarioPorId(id, request);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(usuario, response.getBody());
//        verify(restTemplate, times(1)).exchange(url, GET, new HttpEntity<>(headers), UsuarioRetornoDto.class);
//    }
//
//    @Test
//    @DisplayName("Deve deletar usuário com sucesso")
//    void deletarUsuarioComSucesso() {
//        int id = 1;
//        String url = "http://localhost:8080/users/delete/" + id;
//        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Usuário deletado com sucesso", HttpStatus.OK);
//
//        when(restTemplate.exchange(url, DELETE, new HttpEntity<>(headers), String.class))
//                .thenReturn(expectedResponse);
//
//        ResponseEntity<String> response = service.deletarUsuario(id, request);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Usuário deletado com sucesso", response.getBody());
//        verify(restTemplate, times(1)).exchange(url, DELETE, new HttpEntity<>(headers), String.class);
//    }
//
//    @Test
//    @DisplayName("Deve verificar se as validações foram chamadas")
//    void verificarSeValidacoesForamChamadas() {
//        UsuarioDto dto = mock(UsuarioDto.class);
//        HttpHeaders headersMock = mock(HttpHeaders.class);
//
//        when(HttpHeaderUtil.copyHeaders(request)).thenReturn(headersMock);
//
//        service.criarUsuario(dto, request);
//
//        verify(HttpHeaderUtil, times(1)).copyHeaders(request);
//    }
//}