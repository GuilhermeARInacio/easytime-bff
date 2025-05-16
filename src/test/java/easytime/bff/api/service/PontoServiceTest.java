package easytime.bff.api.service;

import easytime.bff.api.dto.pontos.Status;
import easytime.bff.api.dto.pontos.TimeLogDto;
import easytime.bff.api.dto.usuario.LoginDto;
import easytime.bff.api.util.HttpHeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;

class PontoServiceTest {

    @InjectMocks
    private PontoService pontoService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarPonto_Success() {
        // Arrange
        LoginDto loginDto = new LoginDto("user123");
        String url = "http://localhost/ponto";
        ResponseEntity<TimeLogDto> mockResponse = new ResponseEntity<>(new TimeLogDto("mkenzo", LocalDate.now(), Time.valueOf("08:00:00"), Status.PENDENTE), HttpStatus.OK);

        when(HttpHeaderUtil.copyHeaders(request)).thenReturn(httpHeaders);
        when(restTemplate.exchange(eq(url), eq(POST), any(HttpEntity.class), eq(TimeLogDto.class)))
                .thenReturn(mockResponse);

        // Act
        ResponseEntity<?> response = pontoService.registrarPonto(loginDto, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(restTemplate, times(1))
                .exchange(eq(url), eq(POST), any(HttpEntity.class), eq(TimeLogDto.class));
    }

    @Test
    void testRegistrarPonto_Exception() {
        // Arrange
        LoginDto loginDto = new LoginDto("user123");
        String url = "http://localhost/ponto";

        when(HttpHeaderUtil.copyHeaders(request)).thenReturn(httpHeaders);
        when(restTemplate.exchange(eq(url), eq(POST), any(HttpEntity.class), eq(TimeLogDto.class)))
                .thenThrow(new RuntimeException("Error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> pontoService.registrarPonto(loginDto, request));
        verify(restTemplate, times(1))
                .exchange(eq(url), eq(POST), any(HttpEntity.class), eq(TimeLogDto.class));
    }

    @Test
    void testDeletarPonto_Success() {
        // Arrange
        Integer id = 1;
        String url = "http://localhost/ponto/1";
        ResponseEntity<String> mockResponse = new ResponseEntity<>("Deleted", HttpStatus.OK);

        when(HttpHeaderUtil.copyHeaders(request)).thenReturn(httpHeaders);
        when(restTemplate.exchange(eq(url), eq(DELETE), any(HttpEntity.class), eq(String.class)))
                .thenReturn(mockResponse);

        // Act
        ResponseEntity<?> response = pontoService.deletarPonto(id, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(restTemplate, times(1))
                .exchange(eq(url), eq(DELETE), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void testDeletarPonto_Exception() {
        // Arrange
        Integer id = 1;
        String url = "http://localhost/ponto/1";

        when(HttpHeaderUtil.copyHeaders(request)).thenReturn(httpHeaders);
        when(restTemplate.exchange(eq(url), eq(DELETE), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new RuntimeException("Error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> pontoService.deletarPonto(id, request));
        verify(restTemplate, times(1))
                .exchange(eq(url), eq(DELETE), any(HttpEntity.class), eq(String.class));
    }
}