package at.technikum.bweng.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserAccessPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        UserPrincipal userPrincipal = ((UserPrincipal) authentication.getPrincipal());
        if (userPrincipal.getRole().equals("ROLE_ADMIN")) return true;
        if (targetType.equals("at.technikum.bweng.entity.User") && targetId instanceof UUID uuid) {
            return userPrincipal.getId().equals(uuid);
        }
        return false;
    }
}
