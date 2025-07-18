FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/data-integrator-backend-0.1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]