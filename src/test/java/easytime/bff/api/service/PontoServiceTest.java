package easytime.bff.api.service;

import easytime.bff.api.dto.pontos.*;
import easytime.bff.api.dto.usuario.LoginDto;
import easytime.bff.api.util.HttpHeaderUtil;
import easytime.bff.api.validacoes.alterar_ponto.ValidacaoAlterarPonto;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PontoServiceTest {

    @InjectMocks
    private PontoService pontoService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private HttpServletRequest request;

    @Mock
    private ValidacaoAlterarPonto validacaoAlterarPonto;

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

        ReflectionTestUtils.setField(pontoService, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(pontoService, "urlSrv", "http://localhost:8080/");
        ReflectionTestUtils.setField(pontoService, "validacoes", List.of(validacaoAlterarPonto));
    }

    @AfterEach
    void tearDown() throws Exception {
        staticHttpHeaderUtil.close();
        mocks.close();
    }

    @Test
    void registrarPonto_shouldCallRestTemplate() {
        BaterPonto dto = new BaterPonto("08:00:00");
        ResponseEntity<Object> expected = ResponseEntity.ok("ok");
        when(restTemplate.exchange(
                eq("http://localhost:8080/ponto/"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Object.class)
        )).thenReturn(expected);

        ResponseEntity<Object> result = pontoService.registrarPonto(dto, request);

        assertEquals(expected, result);
    }

    @Test
    void deletarPonto_shouldCallRestTemplate() {
        ResponseEntity<String> expected = ResponseEntity.ok("deleted");
        when(restTemplate.exchange(
                eq("http://localhost:8080/ponto/1"),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(expected);

        ResponseEntity<String> result = pontoService.deletarPonto(1, request);

        assertEquals(expected, result);
    }

    @Test
    void consultarPonto_shouldCallRestTemplate() {
        ConsultaPontoDTO dto = mock(ConsultaPontoDTO.class);
        List<RegistroCompletoDto> list = Collections.emptyList();
        ResponseEntity<List<RegistroCompletoDto>> expected = ResponseEntity.ok(list);

        when(restTemplate.exchange(
                eq("http://localhost:8080/ponto/consulta"),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<List<RegistroCompletoDto>>>any()
        )).thenReturn(expected);

        ResponseEntity<List<RegistroCompletoDto>> result = pontoService.consultarPonto(dto, request);

        assertEquals(expected, result);
    }

    @Test
    void alterarPonto_shouldValidateAndCallRestTemplate() {
        AlterarPontoDto dto = mock(AlterarPontoDto.class);
        ResponseEntity<String> expected = ResponseEntity.ok("");

        doNothing().when(validacaoAlterarPonto).validar(dto);

        when(restTemplate.exchange(
                eq("http://localhost:8080/ponto/alterar"),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(expected);

        ResponseEntity<String> result = pontoService.alterarPonto(dto, request);

        assertEquals(expected, result);
        verify(validacaoAlterarPonto).validar(dto);
    }

    @Test
    void listarPontos_shouldCallRestTemplate() {
        List<RegistroCompletoDto> list = Collections.emptyList();
        ResponseEntity<List<RegistroCompletoDto>> expected = ResponseEntity.ok(list);

        when(restTemplate.exchange(
                eq("http://localhost:8080/ponto/"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<List<RegistroCompletoDto>>>any()
        )).thenReturn(expected);

        ResponseEntity<List<RegistroCompletoDto>> result = pontoService.listarPontos(request);

        assertEquals(expected, result);
    }

    @Test
    void listarPedidos_shouldCallRestTemplate() {
        var pedido = mock(PedidoPonto.class);
        List<PedidoPonto> pedidos = List.of(pedido);
        ResponseEntity<List<PedidoPonto>> expected = ResponseEntity.ok(pedidos);

        when(restTemplate.exchange(
                eq("http://localhost:8080/ponto/pedidos/all"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<List<PedidoPonto>>>any()
        )).thenReturn(expected);

        ResponseEntity<List<PedidoPonto>> result = pontoService.listarPedidos(request);

        assertEquals(expected, result);
    }

    @Test
    void listarFiltrarPedidos_shouldCallRestTemplate() {
        var pedido = mock(PedidoPonto.class);
        List<PedidoPonto> pedidos = List.of(pedido);
        ResponseEntity<List<PedidoPonto>> expected = ResponseEntity.ok(pedidos);
        FiltroPedidos dto = new FiltroPedidos("01/06/2025", "01/06/2025", Status.PENDENTE, "ALTERACAO");

        when(restTemplate.exchange(
                eq("http://localhost:8080/ponto/pedidos/filtrar"),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<List<PedidoPonto>>>any()
        )).thenReturn(expected);

        ResponseEntity<List<PedidoPonto>> result = pontoService.filtrarPedidos(dto, request);

        assertEquals(expected, result);
    }

    @Test
    void consultarPedidoId_shouldCallRestTemplate() {
        AlterarPontoDto pedido = mock(AlterarPontoDto.class);
        ResponseEntity<AlterarPontoDto> expected = ResponseEntity.ok(pedido);
        Integer id = 1;

        when(restTemplate.exchange(
                eq("http://localhost:8080/ponto/pedido/" + id),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(AlterarPontoDto.class)
        )).thenReturn(expected);

        ResponseEntity<AlterarPontoDto> result = pontoService.consultarPedidoId(id, request);

        assertEquals(expected, result);
    }

    @Test
    void aprovarPonto_shouldCallRestTemplate() {
        ResponseEntity<String> expected = ResponseEntity.ok("aprovado");

        when(restTemplate.exchange(
                eq("http://localhost:8080/ponto/aprovar/1"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(expected);

        ResponseEntity<String> result = pontoService.aprovarPonto(1, request);

        assertEquals(expected, result);
    }

    @Test
    void reprovarPonto_shouldCallRestTemplate() {
        ResponseEntity<String> expected = ResponseEntity.ok("reprovado");

        when(restTemplate.exchange(
                eq("http://localhost:8080/ponto/reprovar/1"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(expected);

        ResponseEntity<String> result = pontoService.reprovarPonto(1, request);

        assertEquals(expected, result);
    }
}