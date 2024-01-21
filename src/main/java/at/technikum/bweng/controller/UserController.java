package at.technikum.bweng.controller;

import at.technikum.bweng.dto.TokenResponseDto;
import at.technikum.bweng.dto.UserAdminDto;
import at.technikum.bweng.dto.UserCredentialsDto;
import at.technikum.bweng.dto.UserDetailsDto;
import at.technikum.bweng.dto.UserDto;
import at.technikum.bweng.dto.mapper.UserAdminDtoMapper;
import at.technikum.bweng.dto.mapper.UserDetailsDtoMapper;
import at.technikum.bweng.dto.mapper.UserDtoMapper;
import at.technikum.bweng.exception.StorageException;
import at.technikum.bweng.exception.UserAlreadyExistsException;
import at.technikum.bweng.security.roles.Admin;
import at.technikum.bweng.security.roles.Public;
import at.technikum.bweng.security.roles.User;
import at.technikum.bweng.service.AuthService;
import at.technikum.bweng.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    public UserDetailsDto register(@RequestBody @Valid UserDto userDto) throws UserAlreadyExistsException {
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
    public UserDetailsDto details(@PathVariable UUID id, @RequestBody @Valid UserDetailsDto details) {
        return detailsDtoMapper.from(userService.patch(id, detailsDtoMapper.from(details)));
    }

    @DeleteMapping("/{id}")
    @User
    public void deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);

    }

    @PostMapping("/{id}/profile-picture")
    @PreAuthorize("hasPermission(#id, 'at.technikum.bweng.entity.User', 'update')")
    public UserDetailsDto uploadProfilePicture(@PathVariable UUID id, @RequestParam("file") MultipartFile profilePicture) throws StorageException {
        return detailsDtoMapper.from(userService.uploadProfilePicture(id, profilePicture));
    }

    @GetMapping
    @Admin
    public List<UserAdminDto> getAllUsersWithRole() {
        return userService.getAll().stream().map(userAdminDtoMapper::from).toList();
    }

    @PostMapping("/{id}/role")
    @Admin
    public UserAdminDto changeRole(@PathVariable UUID id,
                                   @RequestBody @Valid @Pattern(regexp = "ROLE_USER|ROLE_STAFF|ROLE_ADMIN") String role) {
        return userAdminDtoMapper.from(userService.updateRole(id, role));
    }

    @PostMapping("/{id}/activeState")
    @Admin
    public UserAdminDto changeActiveState(@PathVariable UUID id) {
        return userAdminDtoMapper.from(userService.toggleUserState(id));
    }

}
