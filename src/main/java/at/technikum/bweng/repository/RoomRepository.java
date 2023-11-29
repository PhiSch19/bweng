package at.technikum.bweng.repository;

import at.technikum.bweng.entity.Room;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface RoomRepository extends ListCrudRepository<Room, UUID> {
}
