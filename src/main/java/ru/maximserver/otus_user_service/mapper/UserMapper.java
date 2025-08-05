package ru.maximserver.otus_user_service.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.maximserver.otus_user_service.entity.UserAccount;
import ru.maximserver.otus_user_service.jooq.gen.tables.records.UserAccountRecord;
import ru.maximserver.otus_user_service.model.UpdateUser;
import ru.maximserver.otus_user_service.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target="id", ignore = true)
    UserAccountRecord toUserAccountRecord(UpdateUser user);

    @Mapping(target = "last_name", source = "lastName")
    @Mapping(target = "first_name", source = "firstName")
    UserAccount toUserAccount(User user);


    User toUser(UserAccountRecord userAccountRecord);
}
