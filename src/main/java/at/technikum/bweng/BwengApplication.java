package at.technikum.bweng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

@ConfigurationPropertiesScan
@ComponentScan(nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class)
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BwengApplication {

    public static void main(String[] args) {
        SpringApplication.run(BwengApplication.class, args);
    }

}
