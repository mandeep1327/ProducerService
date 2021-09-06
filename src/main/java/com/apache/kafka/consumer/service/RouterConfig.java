package com.apache.kafka.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    private  CustomerHandler service;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
        System.out.println("RouterFunction");
        return RouterFunctions.route()
                .GET("/customers/stream",service::withStream)
                .build();

    }
}
