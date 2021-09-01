package com.apache.kafka.producer.service.ports;

import com.apache.kafka.producer.service.model.Customer;
import com.apache.kafka.producer.service.model.Product;

import java.util.List;

public interface PublisherServiceRepository {
    public void onSendToCustomers( String user);
    public void onSendToProducts( String product);
}
