package at.technikum.bweng.service;


import at.technikum.bweng.entity.Room;
import at.technikum.bweng.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Room patchRoom(UUID id, Room updatedRoom) {
        Optional<Room> existingRoomOptional = roomRepository.findById(id);

        if (existingRoomOptional.isPresent()) {
            Room existingRoom = existingRoomOptional.get();

            if (updatedRoom.getName() != null) {
                existingRoom.setName(updatedRoom.getName());
            }
            if (updatedRoom.getCapacity() != null) {
                existingRoom.setCapacity(updatedRoom.getCapacity());
            }
            if (updatedRoom.getCleaningMinutes() != null) {
                existingRoom.setCleaningMinutes(updatedRoom.getCleaningMinutes());
            }

            return roomRepository.save(existingRoom);
        } else {
            return null;
        }
    }

    public Room updateRoom(UUID id, Room updatedRoom) {
        Optional<Room> existingRoomOptional = roomRepository.findById(id);

        if (existingRoomOptional.isPresent()) {
            return roomRepository.save(updatedRoom);
        } else {
            return null;
        }
    }
}
