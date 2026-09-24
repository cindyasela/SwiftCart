package com.tek.swiftcarts.client;


import com.tek.swiftcarts.config.MpesaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

@Bean
    public RestClient restClient(MpesaProperties properties){

        return RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .build();
    }
}
