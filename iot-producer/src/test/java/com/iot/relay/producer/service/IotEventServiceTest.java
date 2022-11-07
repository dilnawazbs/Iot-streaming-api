package com.iot.relay.producer.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.iot.relay.producer.controller.request.IotEventRequest;
import com.iot.relay.producer.model.IotEvent;
import com.iot.relay.producer.stream.IotEventPublisher;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class IotEventServiceTest {
    private IotEventPublisher iotEventPublisher;
    private IotEventService iotEventService;

    @BeforeEach
    void setUp() {
        iotEventPublisher = mock(IotEventPublisher.class);
        iotEventService = new IotEventService(iotEventPublisher);
    }

    @Test
    void testPublishEvents() {
        IotEventRequest request = IotEventRequest.builder().type("Humidity").heartBeat(20).id(1L).name("humidity of room").total(1).clusterId(1L).build();
        List<IotEventRequest> events = new ArrayList<>();
        events.add(request);
        when(iotEventPublisher.send(any(IotEvent.class))).thenReturn(Mono.empty());
        StepVerifier.create(iotEventService.publishEvents(events)).expectNext(Mono.empty());
    }

    @Test
    void testPublishEventsToComplete() {
        IotEventRequest request = IotEventRequest.builder().type("Humidity").heartBeat(20).id(1L).name("humidity of room").total(2).clusterId(1L).build();
        List<IotEventRequest> events = new ArrayList<>();
        events.add(request);
        when(iotEventPublisher.send(any(IotEvent.class))).thenReturn(Mono.empty());
        StepVerifier.create(iotEventService.publishEvents(events)).expectComplete();
    }
}