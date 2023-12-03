package at.technikum.bweng.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationProperties("security.jwt")
public record JwtProperties(String secret) {
}
