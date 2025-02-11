package dev.italomonte.agregadordeinvestimentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Combination of
// @SpringBootConfiguration -> Indicate that the class contains configurations for the Spring Boot application.
// @EnableAutoConfiguration -> Allows autoconfiguration from Spring Boot (initializer).
// @ComponentScan -> Allow o Spring to map all @Controllers, @Services, @Repositories, @RestController and @Component
public class AgregadordeinvestimentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgregadordeinvestimentosApplication.class, args);
	}

}
