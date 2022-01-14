package com.example.business.websocket;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * A class that returns websocket configuration.
 */
@ConditionalOnWebApplication
@Configuration
public class WebSocketConfig {

	/**
	 * For getting a server endpoint exporter
	 * @return a server endpoint exporter
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
	
	/**
	 * For returing the custom Config for this websocket.
	 * @return custom config
	 */
	@Bean
	public CustomConfigurator customConfigurator() {
		return new CustomConfigurator();
	}
}
