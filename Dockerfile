# syntax=docker/dockerfile:1
FROM openjdk:18

WORKDIR /opt/app

ARG JAR_FILE=target/vhs-rental-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]