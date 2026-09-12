# Dockerfile for QualiTrack Platform using Java 26

FROM eclipse-temurin:26-jdk AS build

WORKDIR /app

RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:26-jre AS runtime

ENV SPRING_PROFILES_ACTIVE=prod

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]