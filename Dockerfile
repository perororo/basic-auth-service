FROM amazoncorretto:21-alpine AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle

COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src
COPY webapp webapp

RUN ./gradlew clean build downloadGrafanaOtelJava

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/basic-auth-service-SNAPSHOT.jar app.jar
COPY --from=builder /app/build/libs/grafana-opentelemetry-java.jar grafana-opentelemetry-java.jar

EXPOSE 8080
ENTRYPOINT ["java", "-javaagent:grafana-opentelemetry-java.jar", "-jar", "app.jar"]
