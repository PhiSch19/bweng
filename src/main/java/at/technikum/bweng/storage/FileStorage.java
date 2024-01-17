package at.technikum.bweng.storage;

import at.technikum.bweng.exception.StorageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileStorage {

    String upload(MultipartFile file) throws StorageException;

    InputStream load(String id) throws StorageException;
}

