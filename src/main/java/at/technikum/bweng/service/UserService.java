package at.technikum.bweng.service;


import at.technikum.bweng.entity.User;
import at.technikum.bweng.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // I guess admins should be able to access a route getAllUsers

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
