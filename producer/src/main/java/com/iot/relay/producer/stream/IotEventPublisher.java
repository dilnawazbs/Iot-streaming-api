package com.iot.relay.producer.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.iot.relay.producer.model.IotEvent;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class IotEventPublisher {
    private final StreamBridge streamBridge;
    public IotEventPublisher(final StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public Mono<IotEvent> send(final IotEvent event) {
        log.info("Sending events '{}'", event.getName());
        Message<IotEvent> message = MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.MESSAGE_KEY, event.getId())
                .build();
        streamBridge.send("iot-data", message);
        return Mono.just(event);
    }
}