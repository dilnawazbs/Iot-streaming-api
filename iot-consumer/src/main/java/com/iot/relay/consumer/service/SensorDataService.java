package com.iot.relay.consumer.service;

import com.iot.relay.consumer.mapper.SensorDataMapper;
import com.iot.relay.model.model.SensorData;
import com.iot.relay.model.model.SensorDataEntity;
import com.iot.relay.model.repo.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorDataService {

  @Autowired
  private SensorDataMapper sensorDataMapper;

  @Autowired
  private SensorDataRepository sensorDataRepository;

  /**
   * Save the sensor data in repository
   *
   * @param sensorDataEntity
   */
  public void save(SensorData sensorData) {
    SensorDataEntity sensorDataEntity = sensorDataMapper.fromEventToEntity(
      sensorData
    );
    sensorDataRepository.save(sensorDataEntity);
  }
}
