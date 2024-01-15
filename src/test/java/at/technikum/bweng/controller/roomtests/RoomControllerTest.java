package at.technikum.bweng.controller.roomtests;

import at.technikum.bweng.controller.MovieController;
import at.technikum.bweng.controller.RoomController;
import at.technikum.bweng.dto.MovieDto;
import at.technikum.bweng.dto.RoomDto;
import at.technikum.bweng.dto.mapper.MovieDtoMapperImpl;
import at.technikum.bweng.dto.mapper.RoomDtoMapperImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class RoomControllerTest {
    @Test
    void postRoom() {

        RoomController roomController = new RoomController(
                null,
                new RoomDtoMapperImpl()
        );

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> roomController.postRoom(new RoomDto(
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
    void patchRoom() {

        RoomController roomController = new RoomController(
                null,
                new RoomDtoMapperImpl()
        );

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> roomController.patchRoom(UUID.randomUUID(), new RoomDto(UUID.randomUUID(),null,null,null,null,null))
                )
                .withMessage("ID update not allowed!");
    }

    @Test
    void putRoom() {

        RoomController roomController = new RoomController(
                null,
                new RoomDtoMapperImpl()
        );

        var id = UUID.randomUUID();
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> roomController.putRoom(id, new RoomDto(
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


/*

    @PatchMapping("/{id}")
    @Admin
    public RoomDto patchRoom(@PathVariable UUID id, @RequestBody RoomDto room) {
        if (room.id() == null) {
            throw new IllegalArgumentException("ID update not allowed!");
        }
        return dtoMapper.from(roomService.patchRoom(id, dtoMapper.from(room)));
    }

    @PutMapping("/{id}")
    @Admin
    public RoomDto putRoom(@PathVariable UUID id, @RequestBody @Validated RoomDto room) {
        if (!id.equals(room.id())) {
            throw new IllegalArgumentException("Put not allowed! IDs do not match.");
        }
        return dtoMapper.from(roomService.updateRoom(id, dtoMapper.from(room)));
    }
 */