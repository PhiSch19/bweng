package at.technikum.bweng.service;


import at.technikum.bweng.entity.User;
import at.technikum.bweng.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        // TODO: 22.11.23 ExceptionHandling beim orElseThrow
        return repository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    public void register(String username, String password) {

        if (repository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists!"); // TODO: 02.12.23 Exception-Handling
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role("ROLE_USER")
                .build();
        repository.save(user);
    }

}
