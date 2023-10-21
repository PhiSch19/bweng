package at.technikum.bweng.repository;

import at.technikum.bweng.entity.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface RoomRepository extends CrudRepository<Room, UUID> {

    @Override
    List<Room> findAll();
}
