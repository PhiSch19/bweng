package at.technikum.bweng.controller;

import at.technikum.bweng.dto.TokenResponseDto;
import at.technikum.bweng.dto.UserCredentialsDto;
import at.technikum.bweng.security.roles.Public;
import at.technikum.bweng.service.AuthService;
import at.technikum.bweng.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    @Public
    public void register(@RequestBody @Valid UserCredentialsDto credentials) {
        userService.register(credentials.username(), credentials.password());
    }

    @PostMapping("/token")
    @Public
    public TokenResponseDto token(@RequestBody @Valid UserCredentialsDto credentials) {
        return new TokenResponseDto(authService.authenticate(credentials.username(), credentials.password()));
    }

}
