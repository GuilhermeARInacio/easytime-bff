package easytime.bff.api.service;

import easytime.bff.api.dto.senha.CodigoValidacao;
import easytime.bff.api.dto.senha.EmailRequest;
import easytime.bff.api.util.HttpHeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SenhaServiceTest {

    @InjectMocks
    private SenhaService senhaService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private HttpServletRequest request;

    private AutoCloseable mocks;
    private MockedStatic<HttpHeaderUtil> staticHttpHeaderUtil;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer token");
        staticHttpHeaderUtil = mockStatic(HttpHeaderUtil.class);
        staticHttpHeaderUtil.when(() -> HttpHeaderUtil.copyHeaders(request)).thenReturn(headers);

        ReflectionTestUtils.setField(senhaService, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(senhaService, "urlSrv", "http://localhost:8080/");
    }

    @AfterEach
    void tearDown() throws Exception {
        staticHttpHeaderUtil.close();
        mocks.close();
    }

    @Test
    void redefinirSenha_shouldCallRestTemplate() {
        CodigoValidacao codigo = mock(CodigoValidacao.class);
        ResponseEntity<String> expected = ResponseEntity.ok("ok");

        when(restTemplate.exchange(
                eq("http://localhost:8080/redefine-senha"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(expected);

        ResponseEntity<String> result = senhaService.redefinirSenha(codigo);

        assertEquals(expected, result);
    }

    @Test
    void enviarCodigo_shouldCallRestTemplate() {
        EmailRequest email = mock(EmailRequest.class);
        ResponseEntity<String> expected = ResponseEntity.ok("sent");

        when(restTemplate.exchange(
                eq("http://localhost:8080/send-email"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(expected);

        ResponseEntity<String> result = senhaService.enviarCodigo(email);

        assertEquals(expected, result);
    }
}