package at.technikum.bweng.dto;

import jakarta.validation.constraints.Email;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDetailsDto(String firstname,
                             String lastname,
                             @Email String email,
                             String salutation,
                             String country,
                             UUID id,
                             UUID profilePictureId,
                             LocalDateTime lastUpdatedOn,
                             LocalDateTime createdOn) {
}
