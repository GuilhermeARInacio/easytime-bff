package easytime.bff.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    public boolean validate(String tokenJWT) {
        return tokenJWT != null && !tokenJWT.isEmpty();
    }
}
