/*
* This class is responsible to extracting user information from jwt token.
* In order for this to work the jwt token must contain valid userId username and role
* We could also choose to validate only against id. the choices are implemented in UserPrincipal.
*/

package at.technikum.bweng.security.jwt;
import at.technikum.bweng.security.user.UserPrincipal;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtToPrincipalConverter {
    public UserPrincipal convert(DecodedJWT jwt) {
        return new UserPrincipal(
                UUID.fromString(jwt.getSubject()),
                jwt.getClaim("username").asString(),
                "",
                jwt.getClaim("role").asString()
        );
    }

}
