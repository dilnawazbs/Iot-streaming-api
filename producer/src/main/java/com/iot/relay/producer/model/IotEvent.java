package com.iot.relay.producer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotEvent {
    private Long id;
    private BigDecimal value;
    private OffsetDateTime timeStamp;
    private String type;
    private String name;
    private Long clusterId;
    
}
