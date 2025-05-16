//package easytime.bff.api.controller;
//
//import easytime.bff.api.dto.usuario.LoginDto;
//import easytime.bff.api.service.PontoService;
//import easytime.bff.api.util.ExceptionHandlerUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class PontoControllerTest {
//
//    @InjectMocks
//    private PontoController pontoController;
//
//    @Mock
//    private PontoService pontoService;
//
//    @Mock
//    private HttpServletRequest request;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testRegistrarPonto_Success() {
//        // Arrange
//        LoginDto loginDto = new LoginDto("user123");
//        ResponseEntity<Object> mockResponse = ResponseEntity.ok("Ponto registrado com sucesso");
//        when(pontoService.registrarPonto(loginDto, request)).thenReturn(mockResponse);
//
//        // Act
//        ResponseEntity<?> response = pontoController.registrarPonto(loginDto, request);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Ponto registrado com sucesso", response.getBody());
//        verify(pontoService, times(1)).registrarPonto(loginDto, request);
//    }
//
//    @Test
//    void testRegistrarPonto_InvalidInput() {
//        // Arrange
//        LoginDto loginDto = new LoginDto("");
//
//        // Act
//        ResponseEntity<?> response = pontoController.registrarPonto(loginDto, request);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }
//
//    @Test
//    void testExcluirPonto_Success() {
//        // Arrange
//        Integer id = 1;
//        ResponseEntity<?> mockResponse = ResponseEntity.ok("Ponto excluído com sucesso");
//        when(pontoService.deletarPonto(id, request)).thenReturn(mockResponse);
//
//        // Act
//        ResponseEntity<?> response = pontoController.excluirPonto(id, request);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Ponto excluído com sucesso", response.getBody());
//        verify(pontoService, times(1)).deletarPonto(id, request);
//    }
//
//    @Test
//    void testExcluirPonto_Exception() {
//        // Arrange
//        Integer id = 1;
//        when(pontoService.deletarPonto(id, request)).thenThrow(new RuntimeException("Erro ao excluir ponto"));
//
//        // Act
//        ResponseEntity<?> response = pontoController.excluirPonto(id, request);
//
//        // Assert
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        verify(pontoService, times(1)).deletarPonto(id, request);
//    }
//}