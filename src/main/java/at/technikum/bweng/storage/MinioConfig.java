package at.technikum.bweng.storage;

import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import at.technikum.bweng.config.MinioProperties;

@Configuration
@AllArgsConstructor
public class MinioConfig {
    private final MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(
                        minioProperties.url(),
                        minioProperties.port(),
                        minioProperties.url().contains("https")
                )
                .credentials(
                        minioProperties.user(),
                        minioProperties.password()
                )
                .build();

    }
}
