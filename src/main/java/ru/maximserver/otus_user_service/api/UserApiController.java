package ru.maximserver.otus_user_service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.maximserver.otus_user_service.model.CreatedId;
import ru.maximserver.otus_user_service.model.UpdateUser;
import ru.maximserver.otus_user_service.model.User;
import ru.maximserver.otus_user_service.service.UserService;

@RestController
@RequestMapping("${openapi.userService.base-path:/api/v1}")
@RequiredArgsConstructor
public class UserApiController implements UserApi {

    private final UserService userService;

    @Override
    public Mono<ResponseEntity<CreatedId>> createUser(
            final Mono<UpdateUser> user,
            final ServerWebExchange exchange) {
        return userService.createUser(user)
                .map(createdId -> ResponseEntity.status(HttpStatus.CREATED).body(createdId));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteUser(
            final Long userId,
            final ServerWebExchange exchange){
            return userService.deleteUser(userId)
                    .thenReturn(ResponseEntity.noContent().build());
    }

    @Override
    public Mono<ResponseEntity<User>> findUserById(
            final Long userId,
            final ServerWebExchange exchange) {
        return userService.findUserById(userId).map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> updateUser(
            final Long userId,
            final Mono<UpdateUser> user,
            final ServerWebExchange exchange
    ){
        return userService.updateUser(userId, user)
                .thenReturn(ResponseEntity.noContent().build());
    }

}
