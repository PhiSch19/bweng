package at.technikum.bweng.controller;


import at.technikum.bweng.dto.RoomDto;
import at.technikum.bweng.dto.mapper.RoomDtoMapper;
import at.technikum.bweng.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
public class RoomController {

    private final RoomService roomService;
    private final RoomDtoMapper dtoMapper;

    @GetMapping("/rooms")
    public List<RoomDto> getRooms() {
        return this.roomService.findAllRooms().stream().map(dtoMapper::from).toList();
    }

    @GetMapping("rooms/{id}")
    public RoomDto getRoom(@PathVariable UUID id) {
        return dtoMapper.from(roomService.getRoom(id));
    }

    @PostMapping("/rooms")
    public RoomDto postRoom(@RequestBody @Validated RoomDto room) {
        if (room.id() != null) {
            throw new IllegalArgumentException("Update not allowed!");
        }
        return dtoMapper.from(roomService.addRoom(dtoMapper.from(room)));
    }

    @PatchMapping("/rooms/{id}")
    public RoomDto patchRoom(@PathVariable UUID id, @RequestBody RoomDto room) {
        // Check if the room exists, and if not, return an error or handle it as needed.
        if (room.id() != null) {
            throw new IllegalArgumentException("Patch not allowed!");
        }

        return dtoMapper.from(roomService.patchRoom(id, dtoMapper.from(room)));
    }

    @PutMapping("/rooms/{id}")
    public RoomDto putRoom(@PathVariable UUID id, @RequestBody RoomDto room) {
        if (room.id() != null) {
            throw new IllegalArgumentException("Put not allowed!");
        }

        return dtoMapper.from(roomService.updateRoom(id, dtoMapper.from(room)));
    }
}
