package com.example.business.websocket;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.socket.server.standard.ServerEndpointRegistration;

public class CustomConfigurator extends ServerEndpointRegistration.Configurator implements ApplicationContextAware {

    private static volatile BeanFactory context;

    /**
     * Gets enpoint Instance
     */
    @Override
    public <T> T getEndpointInstance(Class<T> endpoint) throws InstantiationException {
        return context.getBean(endpoint);
    }

    /**
     * Sets the application context
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CustomConfigurator.context = applicationContext;
    }
}