package com.solomennicova.AuthTemplate;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthTemplateApplication.class, args);
	}

	@Bean
	public void configure(){
		Flyway flyway = Flyway.configure()
				.dataSource("jdbc:postgresql://authtemplate-db-1:5432/auth", "postgres", "1234")
				.load();
		flyway.migrate();
	}
}
