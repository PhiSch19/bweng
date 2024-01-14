package at.technikum.bweng.service;


import at.technikum.bweng.entity.File;
import at.technikum.bweng.entity.User;
import at.technikum.bweng.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
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
        return repository.save(user);
    }

    public User register(User user) {

        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists!"); // TODO: 02.12.23 Exception-Handling
        }

        //set defaults and encode pw
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProfilePicture(null);

        return repository.save(user);
    }

    public User uploadProfilePicture(UUID id, MultipartFile profilePicture) throws FileUploadException {
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

}
