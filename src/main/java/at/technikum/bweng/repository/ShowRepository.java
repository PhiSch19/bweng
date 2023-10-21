package at.technikum.bweng.repository;

import at.technikum.bweng.entity.Room;
import at.technikum.bweng.entity.Show;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ShowRepository extends CrudRepository<Show, UUID> {

    @Override
    List<Show> findAll();

}
