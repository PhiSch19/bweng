package at.technikum.bweng.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

    private final CorsProperties cors;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods(cors.allowedMethods().toArray(new String[0]))
                        .allowCredentials(true)
                        .allowedOrigins(cors.allowedOrigins())
                        .allowedHeaders(cors.allowedHeaders().toArray(new String[0]));
            }
        };
    }

}
