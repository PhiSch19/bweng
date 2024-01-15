package at.technikum.bweng.controller.movietests;

import at.technikum.bweng.controller.MovieController;
import at.technikum.bweng.controller.RoomController;
import at.technikum.bweng.dto.MovieDto;
import at.technikum.bweng.dto.RoomDto;
import at.technikum.bweng.dto.mapper.MovieDtoMapperImpl;
import at.technikum.bweng.dto.mapper.RoomDtoMapperImpl;
import at.technikum.bweng.entity.Movie;
import at.technikum.bweng.repository.MovieRepository;
import at.technikum.bweng.service.MovieService;
import at.technikum.bweng.storage.FileStorage;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    @Test
    void patchMovie() {
        MovieDto movie = new MovieDto(UUID.randomUUID(),null,null,null,null,null);

        MovieController movieController = new MovieController(
                null,
                new MovieDtoMapperImpl()
        );

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> movieController.patchMovie(UUID.randomUUID(), movie)
                )
                .withMessage("ID update not allowed!");
    }

    @Test
    void putRoom() {

        MovieController movieController = new MovieController(
                null,
                new MovieDtoMapperImpl()
        );

        var id = UUID.randomUUID();
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> movieController.putMovie(id, new MovieDto(
                        UUID.randomUUID(),
                        null,
                        null,
                        null,
                        null,
                        null
                )))
                .withMessage("Put not allowed! IDs do not match.");
    }
}

