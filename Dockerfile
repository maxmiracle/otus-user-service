FROM alpine/java:17.0.12-jre
RUN apk update && apk add bash
RUN apk --no-cache add curl
WORKDIR /app
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080
