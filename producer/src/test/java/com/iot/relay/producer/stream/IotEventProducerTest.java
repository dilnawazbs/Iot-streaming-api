package com.iot.relay.producer.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.relay.producer.ProducerApplication;
import com.iot.relay.producer.model.IotEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

public class IotEventProducerTest {

  @Test
  void testSend() {
    try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
        TestChannelBinderConfiguration.getCompleteConfiguration(ProducerApplication.class))
        .web(WebApplicationType.NONE)
        .run("--spring.jmx.enabled=false")) {
      IotEventPublisher newsEventProducer = context.getBean(IotEventPublisher.class);
      IotEvent event = IotEvent
        .builder()
        .clusterId(1L)
        .id(1L)
        .name("Temperature of room")
        .type("Temperature")
        .timeStamp(OffsetDateTime.now(ZoneOffset.UTC))
        .value(BigDecimal.valueOf(RandomUtils.nextDouble(25.00D, 100.00D)))
        .build();

      newsEventProducer.send(event);

      ObjectMapper objectMapper = context.getBean(ObjectMapper.class);
      OutputDestination outputDestination = context.getBean(OutputDestination.class);

      Message<byte[]> outputMessage = outputDestination.receive(0, "iot-data.destination");
      MessageHeaders headers = outputMessage.getHeaders();
      IotEvent payload = deserialize(objectMapper, outputMessage.getPayload(), IotEvent.class);

      assertEquals(headers.get(MessageHeaders.CONTENT_TYPE), MediaType.APPLICATION_JSON_VALUE);
      assertNotNull(payload);
      assertEquals(payload.getId(), event.getId());
      assertEquals(payload.getName(), event.getName());
      assertEquals(payload.getClusterId(), event.getClusterId());
    }
  }

  private <T> T deserialize(ObjectMapper objectMapper, byte[] bytes, Class<T> clazz) {
    try {
      return objectMapper.readValue(bytes, clazz);
    } catch (IOException e) {
      return null;
    }
  }
}
