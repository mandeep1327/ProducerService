package com.apache.kafka.consumer.service;

import com.apache.kafka.consumer.inbound.mq.KafkaConsumer;
import com.apache.kafka.producer.service.exception.NotFoundException;
import com.apache.kafka.producer.service.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerHandler {

    @Autowired
    private KafkaConsumer consumer;

    //with functional endpoint stream
    public Mono<ServerResponse> withStream(ServerRequest request){
        Flux< String> customers= consumer.getCustomers();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customers,Customer.class);
    }

    //without functioanl endpoint
    public List<Customer>  withoutStream(){
        Flux< String> flux= consumer.getCustomers();
        List<Customer> customers = Collections.singletonList((Customer) flux.collectList().block());
        if(null==customers || customers.size()==0){
            throw new NotFoundException("Customers Not Found");

        }
        return customers;
    }
}
