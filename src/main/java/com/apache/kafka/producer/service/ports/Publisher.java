package com.apache.kafka.producer.service.ports;

public interface Publisher {
     void publishToBankCustomer(String user);
     void publishTobankProduct(String product);
}
