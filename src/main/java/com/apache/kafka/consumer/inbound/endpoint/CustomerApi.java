package com.apache.kafka.consumer.inbound.endpoint;

import com.apache.kafka.consumer.inbound.endpoint.dto.CustomerResponseDTO;
import com.apache.kafka.consumer.inbound.endpoint.dto.ErrorMessageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Flux;

@Api(value = "Customer", description = "Get Customer API")
@Controller
public interface CustomerApi {


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer found.", response = CustomerResponseDTO.class),
            @ApiResponse(code = 404, message = "Customer Not found.", response = ErrorMessageDTO.class)})

    @RequestMapping(value = "/v1/getAllCustomers",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE,
            method = RequestMethod.GET)
    public ResponseEntity<Flux<CustomerResponseDTO>> getCustomers() ;

}
