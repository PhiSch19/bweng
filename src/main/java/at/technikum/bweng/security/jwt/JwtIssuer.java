/*Creates JWT token including required state (user) information*/

package at.technikum.bweng.security.jwt;

import at.technikum.bweng.property.JwtProperties;
import at.technikum.bweng.security.TokenIssuer;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtIssuer implements TokenIssuer{

    private final JwtProperties jwtProperties;

    @Override
    public String issue(UUID userId, String username, String role){

        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(4, ChronoUnit.HOURS)))
                .withClaim("username",username)
                .withClaim("role", role)
                .sign(Algorithm.HMAC256(jwtProperties.getSecret()));
    }
}
