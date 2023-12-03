package at.technikum.bweng.security;

import java.util.UUID;

public interface TokenIssuer {
    String issue(UUID userId, String username, String role);
}
