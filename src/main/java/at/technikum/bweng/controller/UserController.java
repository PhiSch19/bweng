package at.technikum.bweng.controller;

import at.technikum.bweng.dto.UserCredentialsDto;
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
    public void register(@RequestBody @Valid UserCredentialsDto credentials) {
        userService.register(credentials.username(), credentials.password());
    }

    @PostMapping("/token")
    public String token(@RequestBody @Valid UserCredentialsDto credentials) {
        return authService.authenticate(credentials.username(), credentials.password());
    }

}
