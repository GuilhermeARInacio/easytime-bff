package easytime.bff.api.controller;

import easytime.bff.api.dto.usuario.DadosAutenticacao;
import easytime.bff.api.service.AutenticacaoService;
import easytime.bff.api.util.ExceptionHandlerUtil;
import easytime.bff.api.validacoes.login.ValidacoesLogin;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutenticacaoControllerTest {

    @InjectMocks
    private AutenticacaoController controller;

    @Mock
    private AutenticacaoService service;

    @Mock
    private List<ValidacoesLogin> validacoes;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void autenticar_returns200OnSuccess() {
        DadosAutenticacao dto = mock(DadosAutenticacao.class);
        when(dto.login()).thenReturn("user");
        when(dto.senha()).thenReturn("pass");
        when(service.autenticar(dto)).thenReturn("token");

        ResponseEntity<?> response = controller.autenticar(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("token", response.getBody());
        verify(validacoes).forEach(any());
    }

    @Test
    void autenticar_callsExceptionHandlerOnValidationException() throws Exception {
        DadosAutenticacao dto = mock(DadosAutenticacao.class);
        when(dto.login()).thenReturn("user");
        when(dto.senha()).thenReturn("pass");
        RuntimeException ex = new RuntimeException("validation failed");

        doThrow(ex).when(validacoes).forEach(any());

        // Access private static LOGGER via reflection
        Field loggerField = AutenticacaoController.class.getDeclaredField("LOGGER");
        loggerField.setAccessible(true);
        Logger logger = (Logger) loggerField.get(null);

        try (MockedStatic<ExceptionHandlerUtil> staticUtil = mockStatic(ExceptionHandlerUtil.class)) {
            staticUtil.when(() -> ExceptionHandlerUtil.tratarExcecao(ex, logger))
                    .thenReturn(ResponseEntity.status(500).body("error"));

            controller.autenticar(dto);

            staticUtil.verify(() -> ExceptionHandlerUtil.tratarExcecao(ex, logger), times(1));
        }
    }
}