package at.technikum.bweng.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.UUID;

public record RoomDto(
        UUID id,
        @NotBlank String name,
        @NotNull @Positive @Max(1000) Integer capacity,
        @NotNull @Positive @Max(60) Integer cleaningMinutes,
        LocalDateTime lastUpdatedOn,
        LocalDateTime createdOn) {
}
