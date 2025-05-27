package easytime.bff.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EasytimeBffApplicationMainTest {

    @Test
    void main_shouldRunWithoutExceptions() {
        assertDoesNotThrow(() -> EasytimeBffApplication.main(new String[]{}));
    }
}