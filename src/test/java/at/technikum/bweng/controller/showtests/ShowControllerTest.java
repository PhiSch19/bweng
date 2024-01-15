package at.technikum.bweng.controller.showtests;

import at.technikum.bweng.controller.MovieController;
import at.technikum.bweng.controller.ShowController;
import at.technikum.bweng.dto.MovieDto;
import at.technikum.bweng.dto.ShowDto;
import at.technikum.bweng.dto.mapper.MovieDtoMapperImpl;
import at.technikum.bweng.dto.mapper.ShowsDtoMapperImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ShowControllerTest {
    @Test
    void postShow() {
        ShowController showController = new ShowController(
                null,
                new ShowsDtoMapperImpl()
        );


        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> showController.postShow(new ShowDto(
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
    void putMovie() {

        ShowController showController = new ShowController(
                null,
                new ShowsDtoMapperImpl()
        );

        var id = UUID.randomUUID();
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> showController.putMovie(id, new ShowDto(
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
