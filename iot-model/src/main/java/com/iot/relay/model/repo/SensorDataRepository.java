package com.iot.relay.model.repo;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.iot.relay.model.model.SensorDataEntity;

@Repository
public interface SensorDataRepository extends MongoRepository<SensorDataEntity, String>, SensorDataRepositoryCustom {
  @Aggregation(pipeline = { "{$group: { _id: '', total: {$max: $value }}}" })
  double max();

  @Aggregation(pipeline = { "{$group: { _id: '', total: {$min: $value }}}" })
  double min();

  @Aggregation(pipeline = { "{$group: { _id: '', total: {$avg: $value }}}" })
  double avg();
}
