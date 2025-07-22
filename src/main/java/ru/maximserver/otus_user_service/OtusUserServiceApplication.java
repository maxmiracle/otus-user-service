package ru.maximserver.otus_user_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class OtusUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtusUserServiceApplication.class, args);
	}

}
