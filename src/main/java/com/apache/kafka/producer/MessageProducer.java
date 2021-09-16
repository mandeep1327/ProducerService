package com.apache.kafka.producer;

import com.apache.kafka.producer.service.model.Customer;
import com.apache.kafka.producer.service.model.Product;
import com.apache.kafka.producer.service.ports.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;

/*
This class will publish the message to topic (bank-customers and bank-products)
* */
@Component
public class MessageProducer implements Publisher {
    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);

    @Autowired
    private ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate;

    @Value("${kafka.topic_customer}")
    String kafkaTopic_customer;

    @Value("${kafka.topic_product}")
    String kafkaTopic_product;

    @Override
    public void publishToBankCustomer(String customer) {

        log.info("send to topic={}, {}={},", kafkaTopic_customer, Customer.class.getSimpleName(), customer);
        reactiveKafkaProducerTemplate.send(kafkaTopic_customer, "record", customer)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", customer, senderResult.recordMetadata().offset()))
                .subscribe();
    }

    @Override
    public void publishTobankProduct(String product) {
        log.info("send to topic={}, {}={},", kafkaTopic_product, Product.class.getSimpleName(), product);
        reactiveKafkaProducerTemplate.send(kafkaTopic_product, "record", product)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", product, senderResult.recordMetadata().offset()))
                .subscribe();
    }
}
