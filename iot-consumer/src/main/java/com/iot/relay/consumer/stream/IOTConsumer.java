package com.iot.relay.consumer.stream;

import com.iot.relay.consumer.service.SensorDataService;
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

  private SensorDataService sensorDataService;

  @Bean
  public Consumer<SensorData> iotdata() {
    return (
      sensorData -> {
        log.info("Data received :" + sensorData.getId() + " " + sensorData.getClusterId() + " " + sensorData.getTimeStamp());
        sensorDataService.save(sensorData);
      }
    );
  }
}
