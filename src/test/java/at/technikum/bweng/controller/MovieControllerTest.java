package at.technikum.bweng.controller;

import at.technikum.bweng.dto.MovieDto;
import at.technikum.bweng.dto.mapper.MovieDtoMapperImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class MovieControllerTest {
    @Test
    void postMovie() {

        MovieController movieController = new MovieController(
                null,
                new MovieDtoMapperImpl()
        );

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> movieController.postMovie(new MovieDto(
                        UUID.randomUUID(),
                        null,
                        null,
                        null,
                        null,
                        null
                )))
                .withMessage("Update not allowed!");
    }
}
