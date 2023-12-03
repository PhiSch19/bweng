package at.technikum.bweng.dto;

import jakarta.validation.constraints.NotBlank;

public record UserCredentialsDto(@NotBlank String username, @NotBlank String password) {
}
