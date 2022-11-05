package com.iot.relay.producer.service;

import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Service;

import com.iot.relay.producer.controller.request.IotEventRequest;
import com.iot.relay.producer.mapper.IotEventMapper;
import com.iot.relay.producer.model.IotEvent;
import com.iot.relay.producer.stream.IotEventPublisher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;

@Service
public class IotEventService {
    private final IotEventPublisher iotEventPublisher;

    public IotEventService(final IotEventPublisher iotEventPublisher) {
        this.iotEventPublisher = iotEventPublisher;
    }

    public ParallelFlux<Object> publishEvents(final List<IotEventRequest> events) {
        return Flux.fromIterable(events).parallel(events.size()).map(event -> publishEvent(IotEventMapper.from(event), event.getTotal(), event.getHeartBeat()));
    }

    private Flux<Void> publishEvent(final IotEvent event, final Integer total, final Integer heartBeat) {
        Flux.range(0, total).delayElements(Duration.ofSeconds(heartBeat)).map(count -> iotEventPublisher.send(event)).subscribe();
        return Flux.empty();
    }
}