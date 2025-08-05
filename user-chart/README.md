### Установка helm

Используется готовый postgres subchart от bitnami   
https://github.com/bitnami/charts/tree/main/bitnami/postgresql

Проверка инсталяции хелма, миникуб должен быть запущен, чтобы сверить параметры

```shell
helm install my-release . --dry-run --debug
```

Установка сервиса в миникуб через helm

```shell
helm install my-release . --debug
```

```shell
helm uninstall my-release
```

### Test

http://arch.homework/swagger-ui/index.html  
http://arch.homework/actuator/health

Список плагинов helm
```shell
helm plugin list
```
Установить плагин helm-secrets
```shell
helm plugin install https://github.com/jkroepke/helm-secrets
```

### Подготовка миникуба
Запускаем миникуб, если он не был запущен ранее
```shell
minikube start
```
```shell
minikube addons enable ingress
```

```shell
minikube tunnel
```

```shell
minikube delete
```

```shell
kubectl get po
```

```shell
kubectl get services
```
Показать список релизов
```shell
helm list
```


