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
- It will start: Iot event Producer, Iot event consumer and Iot api to query sensor data. 
- Along with that it will start the 3 kafka brokers, Kafdrop for Kafka Cluster Overview on localhost 9000, and zookeper as shown below

<img width="1041" alt="Screenshot 2022-11-06 at 21 10 52" src="https://user-images.githubusercontent.com/12380793/200192742-1504572e-a621-4291-9f82-ca4ed47526e4.png">

# Limitation
- The startDateTime and endDateTime should be in YYYY-MM-DD or YYYY/MM/DD format now.This should be imporoved to support more formats.

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

<img width="839" alt="Screenshot 2022-11-06 at 21 42 33" src="https://user-images.githubusercontent.com/12380793/200194057-f9e175ac-aa6a-48a9-9cb8-bd6003506610.png">

<img width="823" alt="Screenshot 2022-11-06 at 21 43 20" src="https://user-images.githubusercontent.com/12380793/200194095-262ca664-ec68-4d17-b0cb-99e2c6f1fc04.png">
