package ru.maximserver.otus_user_service.config;

import io.r2dbc.spi.ConnectionFactory;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JooqConfig {

    @Bean
    public DSLContext dsl(ConnectionFactory connectionFactory) {
        return DSL.using(connectionFactory);
    }
}
