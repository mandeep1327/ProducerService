package com.apache.kafka.producer.service.ports;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface ReadFile {
     void readCustomerFile() throws IOException, ParseException;
     void readProductFile() throws IOException, ParseException;
}
