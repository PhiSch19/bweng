package at.technikum.bweng.service;


import at.technikum.bweng.entity.Room;
import at.technikum.bweng.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }


    public List<Room> findAllRooms(){
        return roomRepository.findAll();
    }

    public Room getRoom(UUID id){
        return roomRepository.findById(id).orElseThrow();
    }

    public Room addRoom(Room room){
        return roomRepository.save(room);
    }



}
