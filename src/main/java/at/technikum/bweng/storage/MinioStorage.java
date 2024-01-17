package at.technikum.bweng.storage;

import at.technikum.bweng.config.MinioProperties;
import at.technikum.bweng.exception.StorageException;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioStorage implements FileStorage {

    private final MinioProperties minioProperties;
    private final MinioClient minioClient;

    @Override
    public String upload(MultipartFile file) throws StorageException {
        String uuid = UUID.randomUUID().toString();
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.bucket())
                            .object(uuid)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            throw new StorageException(e.getMessage(), e);
        }
        return uuid;
    }

    @Override
    public InputStream load(String id) throws StorageException {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioProperties.bucket())
                            .object(id)
                            .build()
            );
        } catch (Exception e) {
            throw new StorageException(e.getMessage(), e);
        }
    }
}

