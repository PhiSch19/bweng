package at.technikum.bweng.controller;


import at.technikum.bweng.dto.RoomDto;
import at.technikum.bweng.dto.mapper.RoomDtoMapper;
import at.technikum.bweng.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
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
    public RoomDto postRoom(@RequestBody RoomDto room) {
        return dtoMapper.from(roomService.addRoom(dtoMapper.from(room)));
    }
}
