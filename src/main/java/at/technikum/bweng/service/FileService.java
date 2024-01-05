package at.technikum.bweng.service;

import at.technikum.bweng.entity.File;
import at.technikum.bweng.repository.FileRepository;
import at.technikum.bweng.storage.FileStorage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileStorage fileStorage;
    private final FileRepository repository;

    public File upload(MultipartFile file) throws FileUploadException {
        return repository.save(
                File.builder()
                        .externalId(fileStorage.upload(file))
                        .contentType(file.getContentType())
                        .build());
    }

    public Resource getResource(UUID id) throws FileNotFoundException {
        return new InputStreamResource(fileStorage.load(get(id).getExternalId()));
    }

    public MediaType getMediaType(UUID id) {
        return MediaType.parseMediaType(get(id).getContentType());
    }

    private File get(UUID id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
