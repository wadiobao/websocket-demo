package com.example.demo.websocket.configuration;

import com.example.demo.websocket.constant.WebSocketConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(WebSocketConstant.WebConfigConstants.MAPPING)
                        .allowedOrigins(allowedOrigins.split(","))
                        .allowedMethods(WebSocketConstant.WebConfigConstants.ALLOWED_METHODS)
                        .allowedHeaders(WebSocketConstant.WebConfigConstants.ALLOWED_HEADERS)
                        .allowCredentials(true);
            }
        };
    }
}
