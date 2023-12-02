package at.technikum.bweng.security.jwt;


import at.technikum.bweng.security.user.UserPrincipalAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.sql.ast.tree.expression.Over;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private static final String BEARER_TYPE = "Bearer";

    private final JwtDecoder jwtDecoder;

    private final JwtToPrincipalConverter jwtToPrincipalConverter;


    public JwtAuthenticationFilter(JwtDecoder jwtDecoder, JwtToPrincipalConverter jwtToPrincipalConverter) {
        this.jwtDecoder = jwtDecoder;
        this.jwtToPrincipalConverter = jwtToPrincipalConverter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        extractTokenFromRequest(request)
                .map(jwtDecoder::decode)
                .map(jwtToPrincipalConverter::convert)
                .map(UserPrincipalAuthenticationToken::new)
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));

        filterChain.doFilter(request, response);

    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token) && token.startsWith(BEARER_TYPE)) {
            // basically we return token without the BEARER_TYPE
            return Optional.of(token.substring(BEARER_TYPE.length()));

        }
        return Optional.empty();
    }


}
