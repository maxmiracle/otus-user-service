package ru.maximserver.otus_user_service.mapper;
import org.mapstruct.Mapper;
import ru.maximserver.otus_user_service.jooq.gen.tables.records.UserRecord;
import ru.maximserver.otus_user_service.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserRecord toUserRecord(User user);
}
