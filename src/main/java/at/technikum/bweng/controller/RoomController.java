package at.technikum.bweng.controller;


import at.technikum.bweng.entity.Room;
import at.technikum.bweng.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class RoomController {

  private final RoomService roomService;

  public RoomController(RoomService roomService){
      this.roomService = roomService;
  }

  @GetMapping("/rooms")
  public List<Room> getRooms(){
      return this.roomService.findAllRooms();
  }

  @GetMapping("rooms/{id}")
  public Room getRoom(@PathVariable UUID id){
      return roomService.getRoom(id);
  }

  @PostMapping("/rooms")
  public Room postRoom(@RequestBody  Room room){
      return roomService.addRoom(room);

  }
}
