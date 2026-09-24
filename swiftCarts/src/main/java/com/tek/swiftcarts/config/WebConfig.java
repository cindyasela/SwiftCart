package com.tek.swiftcarts.config;

import com.tek.swiftcarts.security.SwiftCartPrincipalResolver;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final SwiftCartPrincipalResolver swiftCartPrincipalResolver;


    @Override
    public void addArgumentResolvers(
            List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(swiftCartPrincipalResolver);
    }


}
