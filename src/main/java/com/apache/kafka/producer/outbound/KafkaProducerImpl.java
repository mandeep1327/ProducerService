package com.apache.kafka.producer.outbound;

import com.apache.kafka.producer.service.model.Customer;
import com.apache.kafka.producer.service.model.Product;
import com.apache.kafka.producer.service.ports.PublisherServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerImpl implements PublisherServiceRepository {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerImpl.class);


    @Autowired
    private ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate;

    @Value("${kafka.topic_customer}")
    String kafkaTopic_customer;

    @Value("${kafka.topic_product}")
    String kafkaTopic_product;

    @Override
    public void onSendToCustomers(String customer) {

        log.info("send to topic={}, {}={},", kafkaTopic_customer, Customer.class.getSimpleName(), customer);
        reactiveKafkaProducerTemplate.send(kafkaTopic_customer, "record", customer)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", customer, senderResult.recordMetadata().offset()))
                .subscribe();
    }

    @Override
    public void onSendToProducts(String product) {
        log.info("send to topic={}, {}={},", kafkaTopic_product, Product.class.getSimpleName(), product);
        reactiveKafkaProducerTemplate.send(kafkaTopic_product, "record", product)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", product, senderResult.recordMetadata().offset()))
                .subscribe();
    }
}
