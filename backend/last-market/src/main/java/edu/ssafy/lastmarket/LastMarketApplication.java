package edu.ssafy.lastmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LastMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(LastMarketApplication.class, args);
	}

}
