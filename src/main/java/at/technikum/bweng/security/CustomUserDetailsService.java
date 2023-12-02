package at.technikum.bweng.security;

import at.technikum.bweng.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            var user = userService.findByUsername(username);
            return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
        } catch (EntityNotFoundException exception) {
            throw new UsernameNotFoundException(username);
        }
    }
}
