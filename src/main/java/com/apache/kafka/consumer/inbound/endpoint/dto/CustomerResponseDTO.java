package com.apache.kafka.consumer.inbound.endpoint.dto;

import com.apache.kafka.producer.service.model.Customer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerResponseDTO {
private Integer customerid;
private String customerName;

    public CustomerResponseDTO(Integer customerid, String customerName) {
        this.customerid = customerid;
        this.customerName = customerName;
    }
}
