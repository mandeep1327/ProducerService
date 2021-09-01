package com.apache.kafka.consumer.inbound.endpoint;

import com.apache.kafka.consumer.inbound.endpoint.dto.CustomerResponseDTO;
import com.apache.kafka.producer.service.model.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestMapper {


	public Mono<List<CustomerResponseDTO>> employeeModelToDto(List<Customer> customers) {
		Mono<List<CustomerResponseDTO>> response=  Mono.just(customers.stream().map(cust->
				new CustomerResponseDTO(cust.getCustomerId(),cust.getCustomerName())).collect(Collectors.toList()));
		return response;
	}

}