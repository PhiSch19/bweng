package at.technikum.bweng.dto;

import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.UUID;

public record UserDetailsDto(String firstname,
                             String lastname,
                             @Email String email,
                             String salutation,
                             String country,
                             LocalDate birthdate,
                             UUID id) {
}
