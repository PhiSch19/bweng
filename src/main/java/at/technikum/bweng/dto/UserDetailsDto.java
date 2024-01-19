package at.technikum.bweng.dto;

import at.technikum.bweng.validator.CountryConstraint;
import jakarta.validation.constraints.Email;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDetailsDto(String firstname,
                             String lastname,
                             String username,
                             String role,
                             @Email String email,
                             String salutation,
                             @CountryConstraint String country,
                             UUID id,
                             UUID profilePictureId,
                             LocalDateTime lastUpdatedOn,
                             LocalDateTime createdOn) {
}
