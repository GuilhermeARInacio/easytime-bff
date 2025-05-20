package easytime.bff.api.service;

import easytime.bff.api.dto.pontos.ConsultaPontoDTO;
import easytime.bff.api.dto.pontos.RegistroCompletoDto;
import easytime.bff.api.dto.pontos.Status;
import easytime.bff.api.dto.usuario.LoginDto;
import easytime.bff.api.dto.pontos.TimeLogDto;
import easytime.bff.api.util.HttpHeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PontoServiceTest {

    @InjectMocks
    private PontoService pontoService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private HttpServletRequest request;

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

        Field field = PontoService.class.getDeclaredField("restTemplate");
        field.setAccessible(true);
        field.set(pontoService, restTemplate);
    }

    @AfterEach
    void tearDown() {
        // Close the static mock to avoid conflicts
        if (mockedStaticHttpHeaderUtil != null) {
            mockedStaticHttpHeaderUtil.close();
        }
    }

    @Test
    @DisplayName("Should successfully register a point")
    void testRegistrarPonto() {
        // Arrange
        LoginDto loginDto = new LoginDto("user123");
        TimeLogDto timeLogDto = new TimeLogDto("", LocalDate.now(), Time.valueOf(LocalTime.now()), Status.PENDENTE);

        HttpEntity<LoginDto> entity = new HttpEntity<>(Mockito.mock(LoginDto.class), headers);

        when(HttpHeaderUtil.copyHeaders(request)).thenReturn(headers);
        when(restTemplate.exchange("http://localhost:8080/ponto", HttpMethod.POST, entity, Object.class))
                .thenReturn(ResponseEntity.ok(timeLogDto));

        // Act
        ResponseEntity<?> response = pontoService.registrarPonto(loginDto, request);

    }

    @Test
    @DisplayName("Should successfully delete a point")
    void testDeletarPonto() {
        // Arrange
        Integer id = 1;
        String url = "http://localhost:8080/ponto/" + id;

        HttpEntity<LoginDto> entity = new HttpEntity<>(Mockito.mock(LoginDto.class), headers);

        when(HttpHeaderUtil.copyHeaders(request)).thenReturn(headers);
        when(restTemplate.exchange(url, HttpMethod.DELETE, entity, Object.class))
                .thenReturn(ResponseEntity.ok(""));

        // Act
        ResponseEntity<?> response = pontoService.deletarPonto(id, request);
    }

    @Test
    @DisplayName("Should successfully consult points")
    void testConsultarPonto() {
        // Arrange
        RegistroCompletoDto dto = Mockito.mock(RegistroCompletoDto.class);
        HttpEntity<RegistroCompletoDto> entity = new HttpEntity<>(dto, headers);

        when(HttpHeaderUtil.copyHeaders(request)).thenReturn(headers);
        when(restTemplate.exchange("http://localhost:8080/ponto/consulta", HttpMethod.PUT, entity, Object.class))
                .thenReturn(ResponseEntity.ok(Collections.singletonList(dto)));

        // Act
        ResponseEntity<?> response = pontoService.consultarPonto(Mockito.mock(ConsultaPontoDTO.class), request);
    }
}