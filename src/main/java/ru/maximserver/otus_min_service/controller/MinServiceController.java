package ru.maximserver.otus_min_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/minservice")
public class MinServiceController {
    @GetMapping(path = "/ping",
            produces = "application/json")
    public Mono<String> getPing() {
        return Mono.just("pong");
    }
}
