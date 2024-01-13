package at.technikum.bweng.dto;

import at.technikum.bweng.validator.TimeConflictConstraint;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@TimeConflictConstraint
public record ShowDto(
        UUID id,
        @NotNull LocalDateTime startTime,
        @NotNull UUID movieId,
        @NotNull UUID roomId,
        LocalDateTime lastUpdatedOn,
        LocalDateTime createdOn) {
}
