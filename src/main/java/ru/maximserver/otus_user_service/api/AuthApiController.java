package ru.maximserver.otus_user_service.api;

import lombok.RequiredArgsConstructor;
import org.jooq.tools.Longs;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.maximserver.otus_user_service.model.CreatedId;
import ru.maximserver.otus_user_service.model.UpdateUser;
import ru.maximserver.otus_user_service.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("${openapi.authService.base-path:/auth-serv}")
@RequiredArgsConstructor
public class AuthApiController implements AuthApi {

    private final UserService userService;

    private static final Pattern PATTERN = Pattern.compile( "api\\/v1\\/user\\/(\\d*)");

    private static final Mono<ResponseEntity<Void>> UNAUTHORIZED = Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

    @Override
    public Mono<ResponseEntity<Void>> auth(final Optional<String> uri,
                                           final ServerWebExchange exchange) {
        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authorization == null) {
            return UNAUTHORIZED;
        }
        String base64Credentials = authorization.substring("Basic ".length()).trim();
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(decodedBytes, StandardCharsets.UTF_8);
        String[] parts = credentials.split(":", 2);
        String username = parts[0];
        String password = (parts.length > 1) ? parts[1] : "";
        if (uri.isEmpty()) {
            return UNAUTHORIZED;
        }
        var matcher = PATTERN.matcher(uri.get());
        if (!matcher.find()){
            return UNAUTHORIZED;
        }
        String stringId = matcher.group(1);
        if (Objects.isNull(stringId)) {
            return UNAUTHORIZED;
        }
        Long id = Longs.tryParse(stringId);
        if (Objects.isNull(id)) {
            return UNAUTHORIZED;
        }
        return userService.authenticate(username, password, id)
                    .map(result -> {
                        if (result) {
                            return ResponseEntity.ok().build();
                        } else {
                            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                        }
                    });
    }

    @Override
    public Mono<ResponseEntity<Object>> getSignin(
            final ServerWebExchange exchange
    ) {
        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please sign in"));
    }

    @Override
    public Mono<ResponseEntity<CreatedId>> signin(
            final Mono<UpdateUser> createUserRequest,
            final ServerWebExchange exchange
    ) {
        return userService.createUser(createUserRequest)
                .map(createdId -> ResponseEntity.status(HttpStatus.CREATED).body(createdId))
                .onErrorResume(throwable -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CreatedId())));
    }
}
