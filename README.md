# Iot streaming api
 
A pipeline to process the IoT data, consists of 3 component. 
1. Producer -  To send Iot sensor events to a Kafka cluster.
2. Consumer - To consume a sensor events from  Kafka cluster and persists events in database.
3. Secure web service - A secure web service for querying the readings of sensor (e.g average/median/max/min values) of specific sensors or groups of sensors for a specific timeframe. 
