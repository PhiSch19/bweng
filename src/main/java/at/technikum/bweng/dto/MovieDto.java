package at.technikum.bweng.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record MovieDto(UUID id, @NotBlank String name, @NotNull @Positive @Max(360) Integer durationMinutes) {
}
