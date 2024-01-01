package at.technikum.bweng.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;

public record UserDto(
        @JsonUnwrapped @Valid UserCredentialsDto credentials,
        @Valid UserDetailsDto details) {

    @JsonCreator
    public UserDto(
            String username, String password, UserDetailsDto details
    ) {
        this(new UserCredentialsDto(username, password), details);
    }

}
