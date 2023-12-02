package at.technikum.bweng.security.jwt;

import at.technikum.bweng.property.JwtProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtDecoder {
    private final JwtProperties jwtProperties;

    public DecodedJWT decode(String token){

        return JWT.require(Algorithm.HMAC256(jwtProperties.getSecret())).build().verify(token);
    }
}
