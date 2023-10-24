package at.technikum.bweng.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record RoomDto(UUID id,
                      String name,
                      @NotNull @Positive @Max(1000) Integer capacity,
                      @NotNull @Positive @Max(60) Integer cleaningMinutes) {
}
