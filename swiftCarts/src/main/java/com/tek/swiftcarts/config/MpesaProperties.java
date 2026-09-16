package com.tek.swiftcarts.config;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@Data
@ConfigurationProperties(prefix = "mpesa")
@Getter
@Setter
public class MpesaProperties {

    private String consumerSecret;
    private String consumerKey;
    private String baseUrl;

    private String businessShortCode;
    private String passkey;
    private String callbackUrl;



}
