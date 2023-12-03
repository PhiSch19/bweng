package at.technikum.bweng.storage;
import at.technikum.bweng.config.MinioProperties;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class MinioStorage implements FileStorage {


    private final MinioProperties minioProperties;


    private final MinioClient minioClient;


    public MinioStorage(MinioProperties minioProperties, MinioClient minioClient) {
        this.minioProperties = minioProperties;
        this.minioClient = minioClient;
    }


    @Override
    public String upload(MultipartFile file) {
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
            throw new RuntimeException();
        }


        return uuid;
    }


    @Override
    public InputStream load(String id) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioProperties.bucket())
                            .object(id)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

