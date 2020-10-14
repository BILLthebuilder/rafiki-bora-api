package rafikibora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RafikibApplication {

	public static void main(String[] args) {
		SpringApplication.run(RafikibApplication.class, args);
	}

}
