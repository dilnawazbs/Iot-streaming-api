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
- Go to project root folder and run **docker-compose up**
- It will run images of kafka_0-1, kafka_1-1, kafka_2-1, kafdrop and mongo.
- **Note:** Run the iot-producer, iot-consumer and iot-api from the terminal. And not from docker. 
- Run via terminal, Navigate to each of the project iot-producer, iot-consumer and iot-api and run  **mvn spring-boot:run**  command on each of project to start producer, consumer and iot-api application. Now the consumer and producer are connected.
- This will connect the producer and consumer with Kafka broker.

 <img width="1103" alt="Screenshot 2022-11-07 at 19 50 34" src="https://user-images.githubusercontent.com/12380793/200390898-3d87bb87-87fb-48ee-b5e3-ddfbee9ceb0a.png">


# Limitation
- The startDateTime and endDateTime should be in YYYY-MM-DD or YYYY/MM/DD format now.This should be imporoved to support more formats.
- Producer, consumer and iot-api application needs to be run via terminal. There is an issue with connecting with docker which will be improved.

# API Documentation
- [api-docs](https://github.com/dilnawazbs/Iot-streaming-api/tree/main/api-docs) folder holds the API specification for producer and secure webservice in [YAML](https://github.com/dilnawazbs/Iot-streaming-api/tree/main/api-docs) format.

## Event Producer
Link to API specification in [YAML](https://github.com/dilnawazbs/Iot-streaming-api/blob/main/api-docs/producer-open-api.yaml) format.

<img width="1076" alt="Screenshot 2022-11-06 at 20 18 12" src="https://user-images.githubusercontent.com/12380793/200193597-076dfc3e-d3ab-4b0b-8d19-b19a6a0fc10f.png">

Sample to Publish the event using producer for living room temperature on every second (heartbeat = 1) with total of 120 events.

```
curl --location --request POST 'http://localhost:8080/publish-events' \
--header 'Content-Type: application/json' \
--data-raw '[
    {
        "total": 120,
        "type": "TEMPERATURE",
        "heartBeat": 1,
        "id": 1,
        "name": "Living Room Temperature",
        "clusterId": "1"
    }
]'
```
<img width="968" alt="Screenshot 2022-11-06 at 21 38 22" src="https://user-images.githubusercontent.com/12380793/200193866-d71b036d-f29d-4f52-9e90-d4a6e713c5c8.png">


## IOT Sensor api

Link to API specification in [YAML](https://github.com/dilnawazbs/Iot-streaming-api/blob/main/api-docs/iot-api-open-api.yaml) format.

<img width="824" alt="Screenshot 2022-11-06 at 21 50 14" src="https://user-images.githubusercontent.com/12380793/200194434-e7d7a219-4049-4c6e-ad9e-ab3dbbedac26.png">

### Create/Registor user
If user account already created then call /authenticate API directly else create a account first. The sample request to create new user

```
curl --location --request POST 'http://localhost:8082/create/user' \
--header 'Content-Type: application/json' \
--data-raw '[
   {
    "username": "dilnawaz",
    "password" : "password"
 }
]'
```
<img width="972" alt="Screenshot 2022-11-06 at 21 53 59" src="https://user-images.githubusercontent.com/12380793/200194622-63ade7d5-d944-4292-b12d-8dc86d771db3.png">

### Authenticate

Hit authenticate service with user account and passowrd to get JWT token which should be attached in all other request's header. The sample request looks,

```
curl --location --request POST 'http://localhost:8082/authenticate' \
--header 'Content-Type: application/json' \
--data-raw '[
   {
    "username": "dilnawaz",
    "password" : "password"
 }
]'
```
<img width="975" alt="Screenshot 2022-11-06 at 21 55 34" src="https://user-images.githubusercontent.com/12380793/200194710-2341b365-b540-4533-844a-eb26f2ca3211.png">

### Iot Sensor query API

<img width="822" alt="Screenshot 2022-11-06 at 21 46 54" src="https://user-images.githubusercontent.com/12380793/200194461-604e5c56-2432-4e78-ba13-f907d2d90583.png">

Without JWT token could not access any of below APIs, user will get 401 error
<img width="974" alt="Screenshot 2022-11-06 at 22 00 00" src="https://user-images.githubusercontent.com/12380793/200194884-8f73b2e3-4999-4dc9-bbb9-21f7645d288b.png">

### To find Minimum

```
curl --location --request GET 'http://localhost:8082/query/min?startDateTime=2022-11-05&endDateTime=2022-11-06&eventType=TEMPERATURE&clusterId=1'
--header "Authorization: Bearer << TOKEN FROM AUTHENTICATE API>>"
```
<img width="975" alt="Screenshot 2022-11-06 at 22 14 00" src="https://user-images.githubusercontent.com/12380793/200195541-bcb10ba8-0696-4269-ac6f-4b033d6fc674.png">

### To find Maximum

```
curl --location --request GET 'http://localhost:8082/query/max?startDateTime=2022-11-05&endDateTime=2022-11-06&eventType=TEMPERATURE&clusterId=1'
--header "Authorization: Bearer << TOKEN FROM AUTHENTICATE API>>"
```
<img width="972" alt="Screenshot 2022-11-06 at 22 15 24" src="https://user-images.githubusercontent.com/12380793/200195554-c5264e7f-61c0-4ef7-b820-c27b7669a433.png">

### To find Median

```
curl --location --request GET 'http://localhost:8082/query/median?startDateTime=2022-11-05&endDateTime=2022-11-06&eventType=TEMPERATURE&clusterId=1'
--header "Authorization: Bearer << TOKEN FROM AUTHENTICATE API>>"
```
<img width="973" alt="Screenshot 2022-11-06 at 22 15 50" src="https://user-images.githubusercontent.com/12380793/200195565-a9744bb4-260a-4dda-9509-ce8fef7e27e3.png">

### To find Average

```
curl --location --request GET 'http://localhost:8082/query/average?startDateTime=2022-11-05&endDateTime=2022-11-06&eventType=TEMPERATURE&clusterId=1'
--header "Authorization: Bearer << TOKEN FROM AUTHENTICATE API>>"
```

<img width="970" alt="Screenshot 2022-11-06 at 22 16 19" src="https://user-images.githubusercontent.com/12380793/200195584-53ff3613-0679-4e78-a5c4-8a7c0c7c6359.png">

