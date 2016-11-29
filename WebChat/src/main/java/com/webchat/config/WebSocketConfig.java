/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.config;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author filip
 * This is a modified version of the Spring-WebSocket Tutorial.
 * Just for trying and learning websockets atm.
 */

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
 
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new Chathandler(), "/chat");
    }
    
    class Chathandler extends TextWebSocketHandler{
        
        private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
        
        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception{
            sessions.add(session);
        }
        
        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
            for(WebSocketSession s : sessions){
                s.sendMessage(message);
            }
        }
    }
}
