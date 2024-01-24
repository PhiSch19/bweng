package at.technikum.bweng.service;


import at.technikum.bweng.entity.File;
import at.technikum.bweng.entity.User;
import at.technikum.bweng.exception.StorageException;
import at.technikum.bweng.exception.UserAlreadyExistsException;
import at.technikum.bweng.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    public User findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    public User get(UUID id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public User patch(UUID id, User patchedData) {
        User user = get(id);
        if (patchedData.getFirstname() != null) {
            user.setFirstname(patchedData.getFirstname());
        }
        if (patchedData.getLastname() != null) {
            user.setLastname(patchedData.getLastname());
        }
        if (patchedData.getEmail() != null) {
            user.setEmail(patchedData.getEmail());
        }
        if (patchedData.getSalutation() != null) {
            user.setSalutation(patchedData.getSalutation());
        }
        if (patchedData.getCountry() != null) {
            user.setCountry(patchedData.getCountry());
        }
        if (patchedData.getUsername() != null) {
            user.setUsername(patchedData.getUsername());
        }
        return repository.save(user);
    }

    public User password(UUID id, String password) {
        User user = get(id);
        user.setPassword(passwordEncoder.encode(password));
        return repository.save(user);
    }

    public User register(User user) throws UserAlreadyExistsException {

        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        //set defaults and encode pw
        user.setRole("ROLE_USER");
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProfilePicture(null);

        return repository.save(user);
    }

    public User uploadProfilePicture(UUID id, MultipartFile profilePicture) throws StorageException {
        User user = get(id);
        File file = fileService.upload(profilePicture);
        user.setProfilePicture(file);
        return repository.save(user);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User updateRole(UUID id, String role) {
        User user = get(id);
        user.setRole(role);
        return repository.save(user);
    }

    public User toggleUserState(UUID id) {
        User user = get(id);
        // toggle state
        user.setActive(!user.getActive());
        return repository.save(user);
    }

    public void deleteUser(UUID id) {
        User user = get(id);
        repository.delete(user);
    }
}
