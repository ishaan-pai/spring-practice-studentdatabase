FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /workspace

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -q -DskipTests dependency:go-offline

COPY src ./src
RUN ./mvnw -q -DskipTests package

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

LABEL org.opencontainers.image.title="student-springboot"

RUN addgroup --system student && adduser --system --ingroup student student
USER student

COPY --from=build /workspace/target/*.jar /app/app.jar

EXPOSE 8080

ENV SERVER_PORT=8080 \
    SPRING_DATASOURCE_URL=jdbc:postgresql://student-postgres:5432/student \
    SPRING_DATASOURCE_USERNAME=postgres \
    SPRING_DATASOURCE_PASSWORD=postgres

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
