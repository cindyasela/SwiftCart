package com.tek.swiftcarts.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "swiftsauth")
@Getter
@Setter
public class SwiftsAuthProperties {

    private String baseUrl;

}
