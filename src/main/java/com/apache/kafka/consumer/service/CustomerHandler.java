package com.apache.kafka.consumer.service;

import com.apache.kafka.consumer.inbound.mq.KafkaConsumer;
import com.apache.kafka.producer.service.exception.NotFoundException;
import com.apache.kafka.producer.service.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerHandler {

    @Autowired
    private KafkaConsumer repository;

   /* public Mono<ServerResponse> getCustomers(ServerRequest request){
        Flux< String> customers= repository.getCustomers();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customers,Customer.class);
    }*/
    public List<Customer>  getAllCustomers(){
        Flux< String> flux= repository.getCustomers();
        List<Customer> customers = Collections.singletonList((Customer) flux.collectList().block());
        if(null==customers || customers.size()==0){
            throw new NotFoundException("Customers Not Found");

        }
        return customers;
    }
}
