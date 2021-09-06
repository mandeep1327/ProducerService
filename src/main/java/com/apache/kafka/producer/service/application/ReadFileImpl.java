package com.apache.kafka.producer.service.application;

import com.apache.kafka.producer.service.model.Customer;
import com.apache.kafka.producer.service.model.Product;
import com.apache.kafka.producer.service.ports.ReadFile;
import com.apache.kafka.producer.service.ports.Publisher;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class ReadFileImpl implements ReadFile {
     Publisher publisher;
  public ReadFileImpl(Publisher publisher){
      this.publisher = publisher;
  }

    @Override
    public void readCustomerFile() throws IOException, ParseException {
        File resource = new ClassPathResource("customers.txt").getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(resource));

        JSONArray jsonArray = (JSONArray) jsonObject.get("customers");
  List<Customer> customers  = objectMapper.readValue(jsonArray.toJSONString(), new TypeReference<>(){});
        String jsonStr = objectMapper.writeValueAsString(customers);
        publisher.publishToBankCustomer(jsonStr);
    }
    @Override
    public void readProductFile() throws IOException, ParseException {
        File resource = new ClassPathResource("products.txt").getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(resource));

        JSONArray jsonArray = (JSONArray) jsonObject.get("products");
        List<Product> products  = objectMapper.readValue(jsonArray.toJSONString(), new TypeReference<>(){});
        String jsonStr = objectMapper.writeValueAsString(products);
        publisher.publishTobankProduct(jsonStr);
    }



}
