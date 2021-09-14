package com.apache.kafka.consumer.endpoint.rest;

import com.apache.kafka.consumer.endpoint.dto.CustomerResponseDTO;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Flux;

@Controller
public interface CustomerApi {


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer found.", response = CustomerResponseDTO.class),
            })

    @RequestMapping(value = "/customers",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
     ResponseEntity<Flux<CustomerResponseDTO>> getCustomers() ;

}
