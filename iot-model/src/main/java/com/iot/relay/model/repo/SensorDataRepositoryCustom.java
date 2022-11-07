package com.iot.relay.model.repo;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.iot.relay.model.exception.SensorCustomException;

public interface SensorDataRepositoryCustom {
  BigDecimal findMinValueByClusterIdAndTypeAndTimestamp(Long clusterId, String eventType, OffsetDateTime start, OffsetDateTime end) throws SensorCustomException;

  BigDecimal findMaxValueByClusterIdAndTypeAndTimestamp(Long clusterId, String eventType, OffsetDateTime start, OffsetDateTime end) throws SensorCustomException;

  BigDecimal findAvgValueByClusterIdAndTypeAndTimestamp(Long clusterId, String eventType, OffsetDateTime start, OffsetDateTime end) throws SensorCustomException;

  BigDecimal findMedianValueByClusterIdAndTypeAndTimestamp(Long clusterId, String eventType, OffsetDateTime start, OffsetDateTime end) throws SensorCustomException;
}
