## otus-user-service

Параметры сервиса

| ENV VARIABLE     | PURPOSE               |
|------------------|-----------------------|
| USER_DB_USER     | login пользователя db |
| USER_DB_PASSWORD | pass db               |
| USER_DB_PORT     | port db               |
| USER_DB_HOST     | host db               |
| USER_DB_NAME     | database name         |
| USER_DB_SCHEMA   | schema (public)       |

### Команды

#### Собрать приложение

Требуется jdk 17  
Приложение будет создано в папке build/libs
```shell
./gradlew bootJar
```

#### Собрать Dockerfile

```shell
docker build --platform linux/amd64 -t otus-user-service .
```

#### Запустить докер из локально собранного 8080 - minservice/ping

```shell
docker run --name otus-user-service-01 -d -p 8080:8080 otus-user-service
```
#### Тест

```shell
curl --location 'http://localhost:8080/actuator/health'
```

```shell
curl --location 'http://localhost:8080/testservice/ping'
```

#### Опубликовать на dockerhub

Сделать таг для публикации.
```shell
docker tag otus-user-service maxmiracle/otus-user-service:1
```

Или пересобрать с новым тегом.
```shell
docker build --platform linux/amd64 -t maxmiracle/otus-user-service:1 .
```

Опубликовать
```shell
docker push maxmiracle/otus-user-service:1
```

Запустить из dockerhub
```shell
docker run --name otus-user-service-01 -d -p 8888:8080 maxmiracle/otus-user-service:1
```


