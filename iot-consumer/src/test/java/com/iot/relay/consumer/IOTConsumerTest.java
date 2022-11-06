package com.iot.relay.consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EmbeddedKafka
public class IotConsumerTest {
    private static final String TEST_TOPIC = "iotdata";

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    public void testReceivingKafkaEvents() {
        Consumer<Integer, String> consumer = configureConsumer();
        Producer<Integer, String> producer = configureProducer();

        producer.send(new ProducerRecord<>(TEST_TOPIC, 123, "my-test-value"));

        ConsumerRecord<Integer, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, TEST_TOPIC);
        assertNotNull(singleRecord);
        assertEquals(singleRecord.key(), 123);
        assertEquals(singleRecord.value(), "my-test-value");

        consumer.close();
        producer.close();
    }

    private Consumer<Integer, String> configureConsumer() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("group-id", "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        Consumer<Integer, String> consumer = new DefaultKafkaConsumerFactory<Integer, String>(consumerProps)
                .createConsumer();
        consumer.subscribe(Collections.singleton(TEST_TOPIC));
        return consumer;
    }

    private Producer<Integer, String> configureProducer() {
        Map<String, Object> producerProps = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        return new DefaultKafkaProducerFactory<Integer, String>(producerProps).createProducer();
    }
}