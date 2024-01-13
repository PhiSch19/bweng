package at.technikum.bweng.controller;

import at.technikum.bweng.dto.TokenResponseDto;
import at.technikum.bweng.dto.UserAdminDto;
import at.technikum.bweng.dto.UserCredentialsDto;
import at.technikum.bweng.dto.UserDetailsDto;
import at.technikum.bweng.dto.UserDto;
import at.technikum.bweng.dto.mapper.UserAdminDtoMapper;
import at.technikum.bweng.dto.mapper.UserDetailsDtoMapper;
import at.technikum.bweng.dto.mapper.UserDtoMapper;
import at.technikum.bweng.security.roles.Admin;
import at.technikum.bweng.security.roles.Public;
import at.technikum.bweng.service.AuthService;
import at.technikum.bweng.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;
    private final UserDtoMapper userDtoMapper;
    private final UserDetailsDtoMapper detailsDtoMapper;
    private final UserAdminDtoMapper userAdminDtoMapper;

    @PostMapping("/register")
    @Public
    public UserDetailsDto register(@RequestBody @Valid UserDto userDto) {
        return detailsDtoMapper.from(userService.register(userDtoMapper.from(userDto)));
    }

    @PostMapping("/token")
    @Public
    public TokenResponseDto token(@RequestBody @Valid UserCredentialsDto credentials) {
        return new TokenResponseDto(authService.authenticate(credentials.username(), credentials.password()));
    }

    @GetMapping("/{id}/details")
    @PreAuthorize("hasPermission(#id, 'at.technikum.bweng.entity.User', 'read')")
    public UserDetailsDto details(@PathVariable UUID id) {
        return detailsDtoMapper.from(userService.get(id));
    }

    @PatchMapping("/{id}/details")
    @PreAuthorize("hasPermission(#id, 'at.technikum.bweng.entity.User', 'update')")
    public UserDetailsDto details(@PathVariable UUID id, @RequestBody UserDetailsDto details) {
        return detailsDtoMapper.from(userService.patch(id, detailsDtoMapper.from(details)));
    }

    @PostMapping("/{id}/profile-picture")
    @PreAuthorize("hasPermission(#id, 'at.technikum.bweng.entity.User', 'update')")
    public UserDetailsDto uploadProfilePicture(@PathVariable UUID id, @RequestParam("file") MultipartFile profilePicture) throws FileUploadException {
        return detailsDtoMapper.from(userService.uploadProfilePicture(id, profilePicture));
    }

    @GetMapping
    @Admin
    public List<UserAdminDto> getAllUsersWithRole() {
        return userService.getAll().stream().map(userAdminDtoMapper::from).toList();
    }

    @PostMapping("/{id}/role")
    @Admin
    public UserAdminDto changeRole(@PathVariable UUID id, @RequestBody String role) { //TODO: Validation for role
        return userAdminDtoMapper.from(userService.updateRole(id, role));
    }

}
