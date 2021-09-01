package com.apache.kafka.producer.service.application;

import com.apache.kafka.producer.service.model.Customer;
import com.apache.kafka.producer.service.model.Product;
import com.apache.kafka.producer.service.ports.LoadDataService;
import com.apache.kafka.producer.service.ports.PublisherServiceRepository;
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
public class LoadDataServiceImpl implements LoadDataService {
     PublisherServiceRepository repository;
  public LoadDataServiceImpl(PublisherServiceRepository repository){
      this.repository=repository;
  }

    @Override
    public void readCustomerFile() throws IOException, ParseException {
        File resource = new ClassPathResource("customers.txt").getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(resource));

        JSONArray jsonArray = (JSONArray) jsonObject.get("customers");
  List<Customer> customers  = objectMapper.readValue(jsonArray.toJSONString(), new TypeReference<List<Customer>>(){});
        String jsonStr = objectMapper.writeValueAsString(customers);
        repository.onSendToCustomers(jsonStr);
    }
    @Override
    public void readProductFile() throws IOException, ParseException {
        File resource = new ClassPathResource("products.txt").getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(resource));

        JSONArray jsonArray = (JSONArray) jsonObject.get("products");
        List<Product> products  = objectMapper.readValue(jsonArray.toJSONString(), new TypeReference<List<Product>>(){});
        String jsonStr = objectMapper.writeValueAsString(products);
        repository.onSendToProducts(jsonStr);
    }



}
