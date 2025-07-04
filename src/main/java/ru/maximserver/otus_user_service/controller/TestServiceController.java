package ru.maximserver.otus_user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/testservice")
public class TestServiceController {
    @GetMapping(path = "/ping",
            produces = "application/json")
    public Mono<String> getPing() {
        return Mono.just("pong");
    }
}
