package com.iot.relay.producer.controller.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotEventRequest {
    @Positive(message = "Id must be valid")
    private Long id;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Type cannot be null")
    private String type;
    @Positive(message = "Total count must be valid")
    private int total;
    @Min(value = 1, message = "The heartbeat must be of valid range")
    @Max(value = 10, message = "The heartbeat must be of valid range")
    private int heartBeat;
    @NotNull(message = "Cluster id cannot be null")
    private Long clusterId;
}