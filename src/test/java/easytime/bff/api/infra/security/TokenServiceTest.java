package easytime.bff.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    private TokenService tokenService;
    private String secret = "testsecret";

    @BeforeEach
    void setUp() {
        tokenService = new TokenService();
        ReflectionTestUtils.setField(tokenService, "secret", secret);
    }

    @Test
    void validate_shouldReturnTrueForValidToken() {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + 10000))
                .sign(algorithm);

        assertTrue(tokenService.validate(token));
    }

    @Test
    void validate_shouldReturnFalseForExpiredToken() {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() - 10000))
                .sign(algorithm);

        assertFalse(tokenService.validate(token));
    }

    @Test
    void validate_shouldReturnFalseForInvalidToken() {
        String invalidToken = "invalid.token.value";
        assertFalse(tokenService.validate(invalidToken));
    }
}