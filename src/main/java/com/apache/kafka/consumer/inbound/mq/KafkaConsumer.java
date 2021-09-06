package com.apache.kafka.consumer.inbound.mq;

import com.apache.kafka.producer.service.model.Customer;
import com.apache.kafka.producer.service.model.Product;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Component
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private  ReactiveKafkaConsumerTemplate<String,  ?> reactiveKafkaConsumerTemplate;

  //  @KafkaListener(topics ="${kafka.topic_customer}",groupId = "customer")
    public Flux getCustomers() {
      return  reactiveKafkaConsumerTemplate.receiveAutoAck()
                .delayElements(Duration.ofSeconds(2L))
                .doOnNext(consumerRecord -> LOGGER.info("received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
                )
                .map(ConsumerRecord::value)
                .doOnNext(cusstomer -> LOGGER.info("successfully consumed {}={}", Customer.class.getSimpleName(), cusstomer))
              .doOnError(throwable -> LOGGER.error("something bad happened while consuming : {}", throwable.getMessage()));
    }

    @KafkaListener(topics ="${kafka.topic_product}",groupId = "product")
    public Flux getProducts() {
       return reactiveKafkaConsumerTemplate.receiveAutoAck()
                .delayElements(Duration.ofSeconds(2L))
                .doOnNext(consumerRecord -> LOGGER.info("received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
                )
                .map(ConsumerRecord::value)
                .doOnNext(product -> LOGGER.info("successfully consumed {}={}", Product.class.getSimpleName(), product))
                .doOnError(throwable -> LOGGER.error("something bad happened while consuming : {}", throwable.getMessage()));
    }
}
