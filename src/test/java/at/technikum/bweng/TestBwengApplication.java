package at.technikum.bweng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestBwengApplication {

	public static void main(String[] args) {
		SpringApplication.from(BwengApplication::main).with(TestBwengApplication.class).run(args);
	}

}
