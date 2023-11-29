package at.technikum.bweng.repository;

import at.technikum.bweng.entity.Show;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface ShowRepository extends ListCrudRepository<Show, UUID> {
}
