package at.technikum.bweng.controller;


import at.technikum.bweng.dto.RoomDto;
import at.technikum.bweng.dto.mapper.RoomDtoMapper;
import at.technikum.bweng.security.roles.Admin;
import at.technikum.bweng.security.roles.Staff;
import at.technikum.bweng.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;
    private final RoomDtoMapper dtoMapper;

    @GetMapping
    @Staff
    public List<RoomDto> getRooms() {
        return this.roomService.findAllRooms().stream().map(dtoMapper::from).toList();
    }

    @GetMapping("/{id}")
    @Staff
    public RoomDto getRoom(@PathVariable UUID id) {
        return dtoMapper.from(roomService.getRoom(id));
    }

    @PostMapping
    @Admin
    public RoomDto postRoom(@RequestBody @Validated RoomDto room) {
        if (room.id() != null) {
            throw new IllegalArgumentException("Update not allowed!");
        }
        return dtoMapper.from(roomService.addRoom(dtoMapper.from(room)));
    }

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

    @DeleteMapping("/{id}")
    @Admin
    public void deleteRoom(@PathVariable UUID id) {
        roomService.deleteRoom(id);
    }
}
