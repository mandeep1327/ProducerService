package com.apache.kafka.producer.service.exception;

public class NotFoundException extends ClientException {
    public NotFoundException(String message) {
        super(message);
    }
}