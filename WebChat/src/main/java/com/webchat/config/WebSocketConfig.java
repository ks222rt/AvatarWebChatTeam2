/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 *
 * @author filip
 * This is a modified version of the Spring-WebSocket Tutorial.
 * Just for trying and learning websockets atm.
 */

@Configuration
@ComponentScan(basePackages = {"com.webchat"})
@EnableWebSocketMessageBroker
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
 
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
 
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
         registry.addEndpoint("/chat").withSockJS();
    }
}
