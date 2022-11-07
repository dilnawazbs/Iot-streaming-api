package com.iot.relay.producer.mapper;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.apache.commons.lang3.RandomUtils;

import com.iot.relay.producer.controller.request.IotEventRequest;
import com.iot.relay.producer.model.IotEvent;

public class IotEventMapper {
    public static IotEvent from(final IotEventRequest eventRequest) {
        return IotEvent.builder()
            .clusterId(eventRequest.getClusterId())
            .id(eventRequest.getId())
            .name(eventRequest.getName())
            .type(eventRequest.getType())
            .timeStamp(OffsetDateTime.now(ZoneOffset.UTC))
            .value(BigDecimal.valueOf(RandomUtils.nextDouble(25.00D, 100.00D)))
            .build();
    }
}