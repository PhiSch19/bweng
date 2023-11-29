package at.technikum.bweng.service;


import at.technikum.bweng.entity.Room;
import at.technikum.bweng.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoom(UUID id) {
        return roomRepository.findById(id).orElseThrow();
    }

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room patchRoom(UUID id, Room updatedRoom) {
        Room existingRoom = getRoom(id);

        if (updatedRoom.getName() != null) {
            existingRoom.setName(updatedRoom.getName());
        }
        if (updatedRoom.getCapacity() != null) {
            existingRoom.setCapacity(updatedRoom.getCapacity());
        }
        if (updatedRoom.getCleaningMinutes() != null) {
            existingRoom.setCleaningMinutes(updatedRoom.getCleaningMinutes());
        }

        return updateRoom(id, updatedRoom);
    }

    public Room updateRoom(UUID id, Room updatedRoom) {
        getRoom(id);

        return roomRepository.save(updatedRoom);
    }

    public void deleteRoom(UUID id) {
        getRoom(id);

        roomRepository.deleteById(id);
    }
}
