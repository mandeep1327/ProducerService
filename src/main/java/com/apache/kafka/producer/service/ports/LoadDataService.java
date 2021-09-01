package com.apache.kafka.producer.service.ports;


import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface LoadDataService {
    public void readCustomerFile() throws IOException, ParseException;
    public void readProductFile() throws IOException, ParseException;
}
