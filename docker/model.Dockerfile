
FROM maven:3.6.0-jdk-11-slim AS build

COPY src /home/app/src

COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package spring-boot:repackage

FROM openjdk:11-jre-slim

COPY iot-model/target/model-0.0.1-SNAPSHOT.jar iot-model.jar

EXPOSE 8086

ENTRYPOINT ["java","-jar","/iot-model.jar"]
