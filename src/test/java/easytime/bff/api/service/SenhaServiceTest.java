package easytime.bff.api.service;

import easytime.bff.api.dto.CodigoValidacao;
import easytime.bff.api.dto.EmailRequest;
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
import org.springframework.http.*;
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
class SenhaServiceTest {

    @InjectMocks
    private SenhaService service;

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
    @DisplayName("Deve trocar senha com sucesso")
    void trocarSenhaComSucesso() throws NoSuchFieldException, IllegalAccessException {
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.singletonList("Authorization")));
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(httpHeaderUtil.copyHeaders(request)).thenReturn(headers);

        CodigoValidacao mockResponse = Mockito.mock(CodigoValidacao.class);
        HttpEntity<UsuarioDto> entity = new HttpEntity<>(headers);

        when(restTemplate.exchange("http://localhost:8080/senha/redefinir", HttpMethod.POST, entity, String.class)).thenReturn(new ResponseEntity<>("codigo", HttpStatus.OK));

        Field field = SenhaService.class.getDeclaredField("restTemplate");
        field.setAccessible(true);
        field.set(service, restTemplate);

        service.redefinirSenha(mockResponse, request);
    }

    @Test
    @DisplayName("Deve enviar codigo com sucesso")
    void enviarCodigoComSucesso() throws NoSuchFieldException, IllegalAccessException {
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.singletonList("Authorization")));
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(httpHeaderUtil.copyHeaders(request)).thenReturn(headers);

        EmailRequest mockResponse = Mockito.mock(EmailRequest.class);
        HttpEntity<UsuarioDto> entity = new HttpEntity<>(headers);

        when(restTemplate.exchange("http://localhost:8080/senha/enviar-codigo", HttpMethod.POST, entity, String.class)).thenReturn(new ResponseEntity<>("codigo", HttpStatus.OK));

        Field field = SenhaService.class.getDeclaredField("restTemplate");
        field.setAccessible(true);
        field.set(service, restTemplate);

        service.enviarCodigo(mockResponse, request);
    }
}