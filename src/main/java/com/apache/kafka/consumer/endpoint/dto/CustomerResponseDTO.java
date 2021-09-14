package com.apache.kafka.consumer.endpoint.dto;

import lombok.Getter;
import lombok.Setter;

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
