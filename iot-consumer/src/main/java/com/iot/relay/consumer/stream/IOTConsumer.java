package com.iot.relay.consumer.stream;

import com.iot.relay.model.model.SensorData;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Stream listener class which receive all sensor data from stream
 *
 */
@Component
@AllArgsConstructor
@Slf4j
public class IOTConsumer {

  private com.iot.relay.consumer.service.SensorDataService sensorDataService;

  @Bean
  public Consumer<SensorData> iotdata() {
    log.info("Listerner started");
    return (
      sensorData -> {
        log.error("Data received :" + sensorData.getId() + " " + sensorData.getClusterId() + " " + sensorData.getTimeStamp());
        sensorDataService.save(sensorData);
      }
    );
  }
}
