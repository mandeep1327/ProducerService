package com.apache.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/*
This class will consume the message from topic (bank-customers and bank-products)
* */
@Service
public class MessageConsumer {
    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

    // Annotation required to listen the
    // message from kafka server
    @KafkaListener(topics = "bank-customers",
            groupId = "customer")
    public void bankCustomerTopic(String message)
    {
        log.info(
                "You have a new customer message: "
                        + message);
    }

    @KafkaListener(topics = "bank-products",
            groupId = "product")
    public void bankProductTopic(String message)
    {
        log.info(
                "You have a new product message: "
                        + message);
    }

}
