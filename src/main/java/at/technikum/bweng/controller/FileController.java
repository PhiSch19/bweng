package at.technikum.bweng.controller;

import at.technikum.bweng.exception.StorageException;
import at.technikum.bweng.security.roles.User;
import at.technikum.bweng.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @GetMapping("/{id}/download")
    @User
    public ResponseEntity<Resource> download(@PathVariable UUID id) throws StorageException {
        return ResponseEntity.ok()
                .contentType(fileService.getMediaType(id))
                .body(fileService.getResource(id));
    }

}
