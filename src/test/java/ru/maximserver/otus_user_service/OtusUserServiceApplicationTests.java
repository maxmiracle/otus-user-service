package ru.maximserver.otus_user_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
class OtusUserServiceApplicationTests {

	@Container
	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
			DockerImageName.parse("postgres:14.2-alpine"))
			.waitingFor(Wait.forListeningPort())
			.withCommand("postgres", "-c", "max_connections=500");

	@DynamicPropertySource
	static void props(DynamicPropertyRegistry registry) {
		registry.add("spring.r2dbc.url", ()-> postgreSQLContainer.getJdbcUrl().replaceFirst("jdbc:", "r2dbc:"));
		registry.add("spring.r2dbc.username", postgreSQLContainer::getUsername);
		registry.add("spring.r2dbc.password", postgreSQLContainer::getPassword);

		registry.add("spring.liquibase.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.liquibase.user", postgreSQLContainer::getUsername);
		registry.add("spring.liquibase.password", postgreSQLContainer::getPassword);

		registry.add("spring.liquibase.clear-checksums", () -> "false");
	}

	@Test
	void contextLoads() {
	}

}
