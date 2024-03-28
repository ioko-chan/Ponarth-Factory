package com.solomennicova.AuthTemplate;

import com.solomennicova.AuthTemplate.Service.EmailService;
import jakarta.mail.MessagingException;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

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

	@Bean
	public ClassLoaderTemplateResolver secondaryTemplateResolver() {
		ClassLoaderTemplateResolver secondaryTemplateResolver = new ClassLoaderTemplateResolver();
		secondaryTemplateResolver.setPrefix("emailMessage/");
		secondaryTemplateResolver.setSuffix(".html");
		secondaryTemplateResolver.setTemplateMode(TemplateMode.HTML);
		secondaryTemplateResolver.setCharacterEncoding("UTF-8");
		secondaryTemplateResolver.setOrder(1);
		secondaryTemplateResolver.setCheckExistence(true);

		return secondaryTemplateResolver;
	}

}
