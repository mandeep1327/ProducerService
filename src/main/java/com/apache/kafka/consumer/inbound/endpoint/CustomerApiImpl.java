package com.apache.kafka.consumer.inbound.endpoint;

import com.apache.kafka.consumer.inbound.endpoint.dto.CustomerResponseDTO;
import com.apache.kafka.consumer.service.CustomerHandler;
import com.apache.kafka.producer.service.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Component
public class CustomerApiImpl implements CustomerApi {

	private final CustomerHandler service;
	private final RestMapper mapper;
	public CustomerApiImpl(CustomerHandler service, RestMapper mapper)
	{
		this.mapper=mapper;
		this.service = service;
	}

	public ResponseEntity<Flux<CustomerResponseDTO>> getCustomers() {
		List<Customer> customers=service.withoutStream();
		Mono<List<CustomerResponseDTO>> monoList= mapper.employeeModelToDto(customers);
		Flux<CustomerResponseDTO> response=	monoList
				.flatMapIterable(list -> list);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
