package ru.maximserver.otus_user_service.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.maximserver.otus_user_service.model.User;
import ru.maximserver.otus_user_service.service.UserService;

@Controller
@RequestMapping("${openapi.userService.base-path:/api/v1}")
@RequiredArgsConstructor
public class UserApiController implements UserApi {

    private final UserService userService;

    @Override
    public Mono<ResponseEntity<Void>> createUser(Mono<User> user, ServerWebExchange exchange) {
        return userService.createUser(user)
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
    }
}
