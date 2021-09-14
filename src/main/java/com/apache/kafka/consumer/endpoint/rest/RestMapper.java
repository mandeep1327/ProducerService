package com.apache.kafka.consumer.endpoint.rest;

import com.apache.kafka.consumer.endpoint.dto.CustomerResponseDTO;
import com.apache.kafka.producer.service.model.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestMapper {


	public Mono<List<CustomerResponseDTO>> employeeModelToDto(List<Customer> customers) {
		return Mono.just(customers.stream().map(customer->
				new CustomerResponseDTO(customer.getCustomerId(),customer.getCustomerName())).collect(Collectors.toList()));
	}

}