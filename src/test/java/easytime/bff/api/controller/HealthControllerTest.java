package easytime.bff.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class HealthControllerTest {

    @Test
    @DisplayName("Should return 200 and welcome message")
    void health_returns200AndWelcomeMessage() {
        HealthController controller = new HealthController();
        ResponseEntity<?> response = controller.health();

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("Bem-vindo ao EasyTime!", response.getBody());
    }
}