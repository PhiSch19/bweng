package at.technikum.bweng.security;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.UUID;

@Getter
public class UserPrincipal extends User {

    private final UUID id;

    public UserPrincipal(UUID id, String username, String password, String role) {
        super(username, password, Collections.singletonList(new SimpleGrantedAuthority(role)));
        this.id = id;
    }
}
