package at.technikum.bweng.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccessPermissionEvaluator implements PermissionEvaluator {

    private final List<AccessPermission> accessPermissions;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        boolean hasPermission = false;

        for (AccessPermission accessPermission : accessPermissions) {
            if (!accessPermission.supports(authentication, targetType)) {
                continue;
            }

            hasPermission |= accessPermission.hasPermission(authentication, (UUID) targetId);
        }

        return hasPermission;
    }
}
