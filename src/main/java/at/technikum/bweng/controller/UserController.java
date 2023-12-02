package at.technikum.bweng.controller;

import at.technikum.bweng.dto.UserCredentialsDto;
import at.technikum.bweng.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/register")
    public void register(@RequestBody UserCredentialsDto credentials) {
        userService.register(credentials.username(), credentials.password());
    }

}
