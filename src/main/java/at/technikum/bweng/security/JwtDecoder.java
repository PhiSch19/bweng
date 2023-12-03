package at.technikum.bweng.security;

import at.technikum.bweng.config.JwtProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtDecoder {
    
    private final JwtProperties jwtProperties;

    public DecodedJWT decode(String token) {
        return JWT.require(Algorithm.HMAC256(jwtProperties.secret()))
                .withIssuer(jwtProperties.issuer())
                .build()
                .verify(token);
    }
}
