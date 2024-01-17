package at.technikum.bweng.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCredentialsDto(
        @NotBlank @Size(min = 5) String username,
        @NotBlank @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$") String password) {
}
