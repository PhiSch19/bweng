package at.technikum.bweng.repository;

import at.technikum.bweng.entity.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface MovieRepository extends ListCrudRepository<Movie, UUID> {

    @Query(value = "SELECT m FROM Movie m ORDER BY m.createdOn desc")
    List<Movie> getAllSortedByCreationDate();

}
