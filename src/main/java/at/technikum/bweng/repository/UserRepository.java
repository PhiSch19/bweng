package at.technikum.bweng.repository;

import at.technikum.bweng.entity.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends ListCrudRepository<User, UUID> {

    Optional<User> findByUsername(String username);

}
