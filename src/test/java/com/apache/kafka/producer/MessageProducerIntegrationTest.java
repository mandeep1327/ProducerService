package com.apache.kafka.producer;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.converter.MessagingMessageConverter;
import org.springframework.kafka.test.condition.EmbeddedKafkaCondition;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderResult;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@EmbeddedKafka(topics = MessageProducerIntegrationTest.TOPIC, partitions = 1)
 class MessageProducerIntegrationTest {
 public static final String TOPIC = "bank-customers";
 private static final String DEFAULT_VALUE = "test";
 private static final int DEFAULT_KEY = 1;
 private static final Duration DEFAULT_VERIFY_TIMEOUT = Duration.ofSeconds(10);
 private ReactiveKafkaProducerTemplate<Integer, String> reactiveKafkaProducerTemplate;


 @BeforeEach
 public void setUp() {

  this.reactiveKafkaProducerTemplate = new ReactiveKafkaProducerTemplate<>(setupSenderOptionsWithDefaultTopic(),
          new MessagingMessageConverter());
 }
 private SenderOptions<Integer, String> setupSenderOptionsWithDefaultTopic() {
  Map<String, Object> senderProps =
          KafkaTestUtils.producerProps(EmbeddedKafkaCondition.getBroker().getBrokersAsString());
  return SenderOptions.create(senderProps);
 }
 @AfterEach
 public void tearDown() {
  this.reactiveKafkaProducerTemplate.close();
 }

 @Test
 public void shouldNotCreateTemplateIfOptionsIsNull() {
  assertThatIllegalArgumentException()
          .isThrownBy(() -> new ReactiveKafkaProducerTemplate<String, String>(null))
          .withMessage("Sender options can not be null");
 }

 @Test
 public void shouldSendSingleRecordAsValue() {
  Mono<SenderResult<Void>> senderResult =
          this.reactiveKafkaProducerTemplate.send(TOPIC, DEFAULT_VALUE);

        StepVerifier.create(senderResult)
          .assertNext(result -> {
           assertThat(result.recordMetadata())
                   .extracting(RecordMetadata::topic)
                   .isEqualTo(TOPIC);
          })
          .expectComplete()
          .verify(DEFAULT_VERIFY_TIMEOUT);


 }
 @Test
 public void shouldSendSingleRecordAsKeyValue() {
  Mono<SenderResult<Void>> resultMono =
          this.reactiveKafkaProducerTemplate.send(TOPIC, DEFAULT_KEY, DEFAULT_VALUE);

  StepVerifier.create(resultMono)
          .assertNext(senderResult -> {
           assertThat(senderResult.recordMetadata())
                   .extracting(RecordMetadata::topic)
                   .isEqualTo(TOPIC);
          })
          .expectComplete()
          .verify(DEFAULT_VERIFY_TIMEOUT);
 }

}
