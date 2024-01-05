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

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    public User findByUsername(String username) {
        // TODO: 22.11.23 ExceptionHandling beim orElseThrow
        return repository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    public User get(UUID id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
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

}
