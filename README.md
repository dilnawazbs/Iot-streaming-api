# Iot streaming api
 
A pipeline to process the IoT data, consists of 4 component. 
1. Producer -  To send Iot sensor events to a Kafka cluster.
2. Consumer - To consume a sensor events from  Kafka cluster and persists events in database.
3. Secure web service - A secure web service for querying the readings of sensor (e.g average/median/max/min values) of specific sensors or groups of sensors for a specific timeframe.
4. model - provides model and Mongo repository. 

# Technologies
- Java 11
- Springboot
- Maven
- Lombok
- Webflux
- JPA
- Mongo DB
- JWT
- Spring cloud stream
- Kafka
- Docker
- Open api specification

# Requirements
- Java 11
- Mongo DB
- Docker

# Installation steps and running on docker
Download and start the docker engine.
Go to project root folder and run **docker-compose up**
It will start: Iot event Producer, Iot event consumer and Iot api to query sensor data. Alondg with that it will start the 3 kafka brokers, Kafdrop on localhost 9000, and zookeper as shown below

<img width="1041" alt="Screenshot 2022-11-06 at 21 10 52" src="https://user-images.githubusercontent.com/12380793/200192742-1504572e-a621-4291-9f82-ca4ed47526e4.png">

# Limitation
- The startDateTime and endDateTime should be in YYYY-MM-DD or YYYY/MM/DD format now.This should be imporoved to support more formats.
