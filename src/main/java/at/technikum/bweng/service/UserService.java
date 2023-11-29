package at.technikum.bweng.service;


import at.technikum.bweng.entity.User;
import at.technikum.bweng.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User findByUsername(String username) {
        // TODO: 22.11.23 ExceptionHandling beim orElseThrow
        return repository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

}
