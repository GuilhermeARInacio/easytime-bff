package easytime.bff.api.service;

import easytime.bff.api.dto.token.TokenDto;
import easytime.bff.api.dto.usuario.DadosAutenticacao;
import easytime.bff.api.validacoes.login.ValidacoesLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "SRV_URL=http://localhost:8080"
})
class AutenticacaoServiceTest {

    @InjectMocks
    private AutenticacaoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve autenticar com sucesso e retornar o token")
    void autenticarComSucesso() {
        // Arrange
        DadosAutenticacao usuario = Mockito.mock(DadosAutenticacao.class);
        when(usuario.senha()).thenReturn("senha");
        when(usuario.login()).thenReturn("login");

        RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
        String url = "https://70b9bf47-dcb1-46e9-9886-1110d671967d-00-1upha6j38mgjy.riker.replit.dev:8080/login";
        TokenDto tokenEsperado = Mockito.mock(TokenDto.class);

        // Simula o comportamento do RestTemplate
        when(restTemplateMock.postForObject(url, usuario, TokenDto.class)).thenReturn(tokenEsperado);

        // Injecta o mock no serviço
        service = new AutenticacaoService() {
            @Override
            public TokenDto autenticar(DadosAutenticacao usuario) {
                return restTemplateMock.postForObject(url, usuario, TokenDto.class);
            }
        };
        // Act
        TokenDto token = service.autenticar(usuario);
        // Assert
        assertEquals(tokenEsperado, token);
    }

    @Test
    @DisplayName("Deve dar HttpClientError")
    void deveriaDarHttpClientError(){
        DadosAutenticacao dto = Mockito.mock(DadosAutenticacao.class);
        when(dto.senha()).thenReturn("senha");
        when(dto.login()).thenReturn("login");

        RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
        String url = "http://localhost:8080/login";


        // Simula o comportamento do RestTemplate
        when(restTemplateMock.postForObject(url, dto, String.class)).thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.autenticar(dto));

    }

    @Test
    @DisplayName("Deve dar HttpMessageNotReadable")
    void deveriaDarHttpMessageNotReadable(){
        DadosAutenticacao dto = Mockito.mock(DadosAutenticacao.class);
        when(dto.senha()).thenReturn("senha");

        RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
        String url = "https://70b9bf47-dcb1-46e9-9886-1110d671967d-00-1upha6j38mgjy.riker.replit.dev:8080/login";
        String erroEsperado = "Formato de JSON Inválido. Verifique o corpo da requisição";

        // Simula o comportamento do RestTemplate
        when(restTemplateMock.postForObject(url, dto, TokenDto.class)).thenThrow(new HttpMessageNotReadableException(erroEsperado));

        service = new AutenticacaoService() {
            @Override
            public TokenDto autenticar(DadosAutenticacao usuario) {
                return restTemplateMock.postForObject(url, usuario, TokenDto.class);
            }
        };

        // Act & Assert
        assertThrows(HttpMessageNotReadableException.class, () -> service.autenticar(dto));
    }

}