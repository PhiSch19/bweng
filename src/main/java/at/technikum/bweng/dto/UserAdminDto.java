package at.technikum.bweng.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record UserAdminDto(
        @NotEmpty String username,
        @NotEmpty String role,
        @Valid UserDetailsDto details) {
}
