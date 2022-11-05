package com.iot.relay.producer.controller;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.iot.relay.producer.controller.request.IotEventRequest;
import com.iot.relay.producer.service.IotEventService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;

@ExtendWith(SpringExtension.class)
@WebFluxTest(IotEventController.class)
public class IotEventControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private IotEventService iotEventService;
    
    @Test
    void testPublishEvents() {
        when(iotEventService.publishEvents(anyList())).thenReturn(ParallelFlux.from(Flux.empty()));
        IotEventRequest request = IotEventRequest.builder().type("Humidity").heartBeat(2).id(1L).total(1).name("humidity of room").clusterId(1L).build();
        webTestClient.post()
        .uri("/publish-events")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus().isCreated();
        verify(iotEventService, only()).publishEvents(anyList());
    }

    @Test
    void testFailedToPublishOnEmptyEvents() {
        when(iotEventService.publishEvents(anyList())).thenReturn(ParallelFlux.from(Flux.empty()));
        webTestClient.post()
        .uri("/publish-events")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(List.of())
                .exchange()
                .expectStatus().isBadRequest();
        verify(iotEventService, never()).publishEvents(anyList());
    }

    @Test
    void testFailedToPublishOnInvalidEventId() {
        IotEventRequest request = IotEventRequest.builder().type("Humidity").heartBeat(2).id(-1L).name("humidity of room").clusterId(1L).build();
        webTestClient.post()
        .uri("/publish-events")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus().isBadRequest();
        verify(iotEventService, never()).publishEvents(anyList());
    }

    @Test
    void testFailedToPublishOnMissingClusterId() {
        IotEventRequest request = IotEventRequest.builder().type("Humidity").heartBeat(2).id(-1L).name("humidity of room").build();
        webTestClient.post()
        .uri("/publish-events")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus().isBadRequest();
        verify(iotEventService, never()).publishEvents(anyList());
    }

    @Test
    void testFailedToPublishOnInvalidHeartBeat() {
        IotEventRequest request = IotEventRequest.builder().type("Humidity").heartBeat(20).id(-1L).name("humidity of room").clusterId(1L).build();
        webTestClient.post()
        .uri("/publish-events")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(List.of(request))
                .exchange()
                .expectStatus().isBadRequest();
        verify(iotEventService, never()).publishEvents(anyList());
    }
}