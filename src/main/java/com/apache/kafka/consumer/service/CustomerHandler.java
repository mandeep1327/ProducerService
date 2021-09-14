package com.apache.kafka.consumer.service;

import com.apache.kafka.consumer.mq.MessageConsumer;
import com.apache.kafka.producer.service.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerHandler {

    @Autowired
    private MessageConsumer consumer;

    public List<Customer> getCustomers(){
        Flux flux= consumer.getCustomers();
        return Collections.singletonList((Customer) flux.collectList().block());
    }
}
