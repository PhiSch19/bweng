package at.technikum.bweng.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ShowDto(UUID id, LocalDateTime startTime, UUID movieId, UUID roomId) {
}
