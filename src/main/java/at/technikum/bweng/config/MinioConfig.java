package at.technikum.bweng.config;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {

    private final MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.url(), minioProperties.port(), minioProperties.url().contains("https"))
                .credentials(minioProperties.user(), minioProperties.password())
                .build();

    }
}
