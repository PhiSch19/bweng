package at.technikum.bweng.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@EnableConfigurationProperties
@ConfigurationProperties("minio")
public record MinioProperties(String url, int port, String user, String password, String bucket) {
}


