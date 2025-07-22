package ru.maximserver.otus_user_service.entity;

import org.springframework.data.annotation.Id;
import lombok.Data;

@Data
public class UserAccount {
    @Id
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String first_name;
    private String last_name;
}
