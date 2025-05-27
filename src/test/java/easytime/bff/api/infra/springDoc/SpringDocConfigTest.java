package easytime.bff.api.infra.springDoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpringDocConfigTest {

    @Test
    void springDocOpenAPI_shouldReturnOpenAPIWithExpectedInfo() {
        SpringDocConfig config = new SpringDocConfig();
        OpenAPI openAPI = config.springDocOpenAPI();
        Info info = openAPI.getInfo();

        assertNotNull(info);
        assertEquals("Easytime - Sistema de marcação de ponto (BFF)", info.getTitle());
        assertEquals("1.0", info.getVersion());
        assertTrue(info.getDescription().contains("Easytime é um sistema de marcação de ponto"));
    }
}