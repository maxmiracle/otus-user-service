## otus-user-service

### Команды

#### Собрать приложение

Требуется jdk 17  
Приложение будет создано в папке build/libs
```shell
./gradlew bootJar
```

#### Собрать Dockerfile

```shell
docker build --platform linux/amd64 -t otus-min-service .
```

#### Запустить докер из локально собранного 8000 - health, 8080 - minservice/ping

```shell
docker run --name otus-min-service-01 -d -p 8000:8000 -p 8080:8080 otus-min-service
```
#### Тест

```shell
curl --location 'http://localhost:8000/health'
```

```shell
curl --location 'http://localhost:8080/minservice/ping'
```

#### Опубликовать на dockerhub

Сделать таг для публикации.
```shell
docker tag otus-min-service maxmiracle/otus-min-service:1
```

Или пересобрать с новым тегом.
```shell
docker build --platform linux/amd64 -t maxmiracle/otus-min-service:1 .
```

Опубликовать
```shell
docker push maxmiracle/otus-min-service:1
```

Запустить из dockerhub
```shell
docker run --name otus-min-service-01 -d -p 8000:8000 -p 8888:8080 maxmiracle/otus-min-service:1
```


