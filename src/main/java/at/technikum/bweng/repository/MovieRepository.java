package at.technikum.bweng.repository;

import at.technikum.bweng.entity.Movie;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface MovieRepository extends ListCrudRepository<Movie, UUID> {
}
