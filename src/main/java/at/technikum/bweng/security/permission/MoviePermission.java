package at.technikum.bweng.security.permission;

import at.technikum.bweng.entity.Movie;
import at.technikum.bweng.security.user.UserPrincipal;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public class MoviePermission implements AccessPermission{

    @Override
    public boolean supports(Authentication authentication, String className) {
        return className.equals(Movie.class.getName());
    }

    @Override
    public boolean hasPermission(Authentication authentication, UUID resourceId) {
        return ((UserPrincipal) authentication.getPrincipal()).getId().equals(resourceId);
    }

}
