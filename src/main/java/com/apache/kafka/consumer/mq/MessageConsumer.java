package com.apache.kafka.consumer.mq;

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

@Component
public class MessageConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    @Autowired
    private  ReactiveKafkaConsumerTemplate<String,  ?> reactiveKafkaConsumerTemplate;

    public Flux getCustomers() {
      return  reactiveKafkaConsumerTemplate.receiveAutoAck()
                .doOnNext(consumerRecord -> LOGGER.info("received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
                )
                .map(ConsumerRecord::value)
                .doOnNext(customer -> LOGGER.info("successfully consumed {}={}", Customer.class.getSimpleName(), customer))
              .doOnError(throwable -> LOGGER.error("something bad happened while consuming : {}", throwable.getMessage()));
    }

    @KafkaListener(topics ="${kafka.topic_product}",groupId = "product")
    public Flux getProducts() {
       return reactiveKafkaConsumerTemplate.receiveAutoAck()
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
