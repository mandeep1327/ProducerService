package com.apache.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.condition.EmbeddedKafkaCondition;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.kafka.test.assertj.KafkaConditions.value;

@EmbeddedKafka(topics = { MessageConsumerIntegrationTest.TOPIC })
 class MessageConsumerIntegrationTest {

    public static final String TOPIC = "bank-customers";

    private static EmbeddedKafkaBroker embeddedKafka;

    private static Consumer<Integer, String> consumer;

    @BeforeEach
      void setUp() {
        embeddedKafka = EmbeddedKafkaCondition.getBroker();
        Map<String, Object> consumerProps = KafkaTestUtils
                .consumerProps("KafkaTemplatetests" + UUID.randomUUID(), "false", embeddedKafka);
        DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        consumer = cf.createConsumer();
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, TOPIC);
    }

    @AfterAll
     static void tearDown() {
        consumer.close();
    }

    @Test
    void testWithMessage() {

        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        DefaultKafkaProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<>(senderProps);
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf, true);

        Message<String> message1 = MessageBuilder.withPayload("test")
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .build();

        template.send(message1);

        ConsumerRecord<Integer, String> record = KafkaTestUtils.getSingleRecord(consumer, TOPIC);
        assertThat(record).has(value("test"));
    }
}

