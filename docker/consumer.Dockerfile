FROM maven:3.6.0-jdk-11-slim AS build

COPY src /home/app/src

COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package spring-boot:repackage

FROM openjdk:11-jre-slim

COPY iot-consumer/target/consumer-0.0.1-SNAPSHOT.jar iot-consumer.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/iot-consumer.jar"]