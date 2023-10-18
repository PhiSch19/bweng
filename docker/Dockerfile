# Build stage
FROM maven:3.8.7-eclipse-temurin-17 AS build
COPY src /app/src
COPY pom.xml /app
COPY checkstyle.xml /app
RUN mvn -f /app/pom.xml clean verify

# Run stage
FROM openjdk:17-jdk-slim
COPY --from=build /app/target/be_webeng_23ws-0.0.1.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]
