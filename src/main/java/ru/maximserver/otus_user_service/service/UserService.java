package ru.maximserver.otus_user_service.service;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.maximserver.otus_user_service.mapper.UserMapper;
import ru.maximserver.otus_user_service.model.User;
import static ru.maximserver.otus_user_service.jooq.gen.tables.User.USER;

@Service
@RequiredArgsConstructor
public class UserService {
    private final DSLContext dslContext;
    private final UserMapper userMapper;

    public Mono<Void> createUser(Mono<User> user) {
        return user.map(userMapper::toUserRecord)
                .map(userRecord -> dslContext.insertInto(USER).set(userRecord))
                .map(Query::execute)
                .then();
    }
}
