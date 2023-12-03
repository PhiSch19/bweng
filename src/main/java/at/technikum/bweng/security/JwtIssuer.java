package at.technikum.bweng.security;

import at.technikum.bweng.config.JwtProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtIssuer implements TokenIssuer {

    private final JwtProperties jwtProperties;
    private final Clock clock;

    @Override
    public String issue(UUID userId, String username, String role) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withIssuer(jwtProperties.issuer())
                .withExpiresAt(Instant.now(clock).plus(Duration.of(2, ChronoUnit.HOURS)))
                .withClaim("username", username)
                .withClaim("role", role)
                .sign(Algorithm.HMAC256(jwtProperties.secret()));
    }
}
