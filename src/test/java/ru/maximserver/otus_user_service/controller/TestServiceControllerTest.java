package ru.maximserver.otus_user_service.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(controllers = TestServiceController.class)
public class TestServiceControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetPing() {
        webTestClient.get().uri("/testservice/ping")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("pong");
    }
}

