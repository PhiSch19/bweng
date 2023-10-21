package at.technikum.bweng.repository;

import at.technikum.bweng.entity.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MovieRepository extends CrudRepository<Movie, UUID> {
    @Override
    public List<Movie> findAll();
}
