package at.technikum.bweng.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "security.cors")
public record CorsProperties(String allowedOrigins, List<String> allowedMethods) {
}