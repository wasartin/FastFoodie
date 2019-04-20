package com.example.business.websocket;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@ConditionalOnWebApplication
@Configuration
public class WebSocketConfig {

	/**
	 * returns a new server enpoint exporter
	 * @return a new server endpoint exporter
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
	
	/**
	 * returns a new custom configurator
	 * @return new custom configurator
	 */
	@Bean
	public CustomConfigurator customConfigurator() {
		return new CustomConfigurator();
	}
}
