package at.technikum.bweng.service;

import at.technikum.bweng.security.TokenIssuer;
import at.technikum.bweng.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final TokenIssuer tokenIssuer;
    private final AuthenticationManager authenticationManager;

    public String authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return tokenIssuer.issue(principal.getId(), principal.getUsername(), principal.getRole());
    }

}
