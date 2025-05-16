package easytime.bff.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class HealthControllerTest {
    @Test
    @DisplayName("Teste rota health, deve retornar 200")
    void deveRetornar200() {
        HealthController healthController = new HealthController();
        var response = healthController.health();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Bem-vindo ao EasyTime!", response.getBody());
    }
}