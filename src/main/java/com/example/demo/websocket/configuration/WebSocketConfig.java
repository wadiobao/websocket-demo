package com.example.demo.websocket.configuration;

import com.example.demo.websocket.constant.WebSocketConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Value("${app.cors.allowed-origins}")
	private String allowedOrigins;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(WebSocketConstant.WebSocketConfigConstants.ENDPOINT).setAllowedOriginPatterns(allowedOrigins).withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes(WebSocketConstant.WebSocketConfigConstants.APP_PREFIX);
		registry.enableSimpleBroker(WebSocketConstant.WebSocketConfigConstants.TOPIC);
	}
}
