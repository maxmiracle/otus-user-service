package ru.maximserver.otus_user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import ru.maximserver.otus_user_service.exception.ResourceNotFoundException;
import ru.maximserver.otus_user_service.mapper.UserMapper;
import ru.maximserver.otus_user_service.model.CreatedId;
import ru.maximserver.otus_user_service.model.UpdateUser;
import ru.maximserver.otus_user_service.model.User;

import static ru.maximserver.otus_user_service.jooq.gen.tables.UserAccount.USER_ACCOUNT;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    private final DSLContext dslContext;

    public Mono<CreatedId> createUser(Mono<UpdateUser> user) {
        return user.map(userMapper::toUserAccountRecord)
                .flatMap(userAccountRecord ->
                        Mono.from(dslContext.insertInto(USER_ACCOUNT).set(userAccountRecord).returning()))
                .doOnNext(savedResult -> log.info("Inserted Record:\n{}", savedResult))
                .map(savedResult -> new CreatedId().id(savedResult.getValue(USER_ACCOUNT.ID)));
    }

    public Mono<Void> deleteUser(Long userId) {
      return Mono.from(dslContext.delete(USER_ACCOUNT)
                    .where(USER_ACCOUNT.ID.eq(userId)).returning())
              .switchIfEmpty(Mono.error(userNotFound(userId)))
              .doOnNext(savedResult -> log.info("Deleted Record:\n{}", savedResult))
              .then();
    }

    public Mono<User> findUserById(Long userId) {
        return Mono.from(dslContext.selectFrom(USER_ACCOUNT)
                    .where(USER_ACCOUNT.ID.eq(userId)))
                .switchIfEmpty(Mono.error(userNotFound(userId)))
                .doOnNext(result -> log.info("Found Record:\n{}", result))
                .map(userMapper::toUser);
    }


    public Mono<Void> updateUser(Long userId, Mono<UpdateUser> user) {
        return user.map(userMapper::toUserAccountRecord)
                .flatMap(userAccountRecord -> Mono.from(dslContext.update(USER_ACCOUNT).set(userAccountRecord)
                        .where(USER_ACCOUNT.ID.eq(userId)).returning()))
                .switchIfEmpty(Mono.error(userNotFound(userId)))
                .doOnNext(savedResult -> log.info("Updated Record:\n{}", savedResult))
                .then();
    }

    private ResourceNotFoundException userNotFound(Long userId) {
        log.info("User {} not found", userId);
        return new ResourceNotFoundException("User not found");
    }

    public Mono<Boolean> authenticate(String username, String password) {
        return Mono.from(dslContext.selectFrom(USER_ACCOUNT)
                .where(USER_ACCOUNT.USERNAME.eq(username).and(USER_ACCOUNT.PASSWORD.eq(password))))
                .doOnNext(result -> log.info("Found Record:\n{}", result))
                .map(record -> true)
                .switchIfEmpty(Mono.just(false));
    }
}
