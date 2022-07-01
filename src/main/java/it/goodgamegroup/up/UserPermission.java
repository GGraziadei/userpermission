package it.goodgamegroup.up;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableJpaRepositories
@ComponentScan
@SpringBootApplication
public class UserPermission {

	public static void main(String[] args) {
		SpringApplication.run(UserPermission.class, args);
	}

}
