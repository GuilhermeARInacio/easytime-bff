package easytime.bff.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${JWT_SECRET}")
    private String secret;

    public boolean validate(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJwt = JWT.require(algorithm).build().verify(tokenJWT);

            return decodedJwt.getExpiresAt().after(new Date());
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getLogin(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJwt = JWT.require(algorithm).build().verify(tokenJWT);
            return decodedJwt.getSubject();
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("Token inválido ou expirado.");
        }
    }
}
