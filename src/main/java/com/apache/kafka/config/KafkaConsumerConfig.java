package com.apache.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    @Bean
    public Map<String, Object> kafkaConsumerConfiguration() {
        Map<String, Object> configuration = new HashMap<>();
        configuration.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configuration.put(ConsumerConfig.GROUP_ID_CONFIG, "customer");
        configuration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        configuration.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        configuration.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class.getName());
        configuration.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return configuration;
    }

    @Bean
    ReceiverOptions<String,  String> kafkaReceiverOptions(@Value("${kafka.topic.in}") String[] inTopicName) {
        ReceiverOptions<String, String> options = ReceiverOptions.create(kafkaConsumerConfiguration());
        return options.subscription(Arrays.asList(inTopicName))
          .withKeyDeserializer(new StringDeserializer())
                .withValueDeserializer(new JsonDeserializer());
    }


    @Bean
    public ReactiveKafkaConsumerTemplate<String, ?> reactiveKafkaConsumerTemplate(ReceiverOptions<String, ?> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<>(kafkaReceiverOptions);
    }
}
