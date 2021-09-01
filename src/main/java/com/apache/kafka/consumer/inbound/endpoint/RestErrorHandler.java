package com.apache.kafka.consumer.inbound.endpoint;

import com.apache.kafka.consumer.inbound.endpoint.dto.ErrorMessageDTO;
import com.apache.kafka.producer.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.util.Collections;

@RestControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestErrorHandler.class);


    public RestErrorHandler() {

    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageDTO processRuntimeException(Exception ex) {
        LOGGER.error("Returning 500 error to user", ex);
        ErrorMessageDTO messages = new ErrorMessageDTO();
        String errorMessage = "Internal server error, timestamp: " + new Timestamp(System.currentTimeMillis()) ;
        messages.setErrors(Collections.singletonList(errorMessage));
        return messages;
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageDTO processNotFoundException(Exception ex) {
        ErrorMessageDTO messages = new ErrorMessageDTO();
        String errorMessage = " Resource not found, timestamp: " + new Timestamp(System.currentTimeMillis())  ;
        messages.setErrors(Collections.singletonList(ex.getMessage() + errorMessage));
        return messages;
    }
}
