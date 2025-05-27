package easytime.bff.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
		"SRV_URL=http://localhost:8080"
})
class EasytimeBffApplicationTests {

	@Test
	void contextLoads() {
	}

}
