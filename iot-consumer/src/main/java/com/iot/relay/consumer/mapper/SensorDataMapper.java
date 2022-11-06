package com.iot.relay.consumer.mapper;

import com.iot.relay.model.model.SensorData;
import com.iot.relay.model.model.SensorDataEntity;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;

/**
 * Mapper class to map sensor data object to mongoDB entity
 *
 */
@Component
public class SensorDataMapper {

  /**
   * Maps iot stream data to entity
   *
   * @param sensor input data
   * @return the entity to store it in database
   */
  public SensorDataEntity fromEventToEntity(SensorData sensorData) {
    return SensorDataEntity
      .builder()
      .name(sensorData.getName())
      .id(sensorData.getId())
      .value(sensorData.getValue())
      .type(sensorData.getType())
      .clusterId(sensorData.getClusterId())
      .timestamp(OffsetDateTime.parse(sensorData.getTimeStamp()))
      .build();
  }
}
