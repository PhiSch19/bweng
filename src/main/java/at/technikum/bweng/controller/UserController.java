package at.technikum.bweng.controller;

import at.technikum.bweng.dto.TokenResponseDto;
import at.technikum.bweng.dto.UserCredentialsDto;
import at.technikum.bweng.dto.UserDetailsDto;
import at.technikum.bweng.dto.UserDto;
import at.technikum.bweng.dto.mapper.UserDetailsDtoMapper;
import at.technikum.bweng.dto.mapper.UserDtoMapper;
import at.technikum.bweng.security.roles.Public;
import at.technikum.bweng.service.AuthService;
import at.technikum.bweng.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;
    private final UserDtoMapper userDtoMapper;
    private final UserDetailsDtoMapper detailsDtoMapper;

    @PostMapping("/register")
    @Public
    @ResponseStatus(code = HttpStatus.CREATED)
    public void register(@RequestBody @Valid UserDto userDto) {
        userService.register(userDtoMapper.from(userDto));
    }

    @PostMapping("/token")
    @Public
    public TokenResponseDto token(@RequestBody @Valid UserCredentialsDto credentials) {
        return new TokenResponseDto(authService.authenticate(credentials.username(), credentials.password()));
    }

    @GetMapping("/{id}/details")
    @Public //TODO 1.1.2024 Authentifizierung nur so, dass sein eigener sichtbar ist oder man Admin sein muss
    public UserDetailsDto details(@PathVariable UUID id) {
        return detailsDtoMapper.from(userService.get(id));
    }

}
