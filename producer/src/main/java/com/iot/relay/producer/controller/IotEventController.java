package com.iot.relay.producer.controller;

import com.iot.relay.producer.controller.request.IotEventRequest;
import com.iot.relay.producer.service.IotEventService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Validated
@RestController
@Slf4j
@RequestMapping("/publish-events")
public class IotEventController {

  private final IotEventService iotEventService;

  public IotEventController(IotEventService iotEventService) {
    this.iotEventService = iotEventService;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @Operation(description = "Publishes the events to kafka broker")
  public Mono<Void> publishEvents(@Size(min = 1, max = 10, message = "Check the limit of messages") @RequestBody List<@Valid IotEventRequest> request) {
    iotEventService.publishEvents(request)
    .doOnSubscribe(subscription -> log.info("Received the iot event {}", request))
    .subscribe(null, error -> log.error("Failed to process iot event {} with error {}" ,request, error.getMessage()),
                        () -> log.info("event {} has processed", request));
      return Mono.empty();
  }
}
