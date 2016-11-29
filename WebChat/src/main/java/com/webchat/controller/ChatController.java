/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.controller;

import com.webchat.model.Message;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author filip
 */
@Controller
public class ChatController {
    
    @RequestMapping(value = "/main/chat", method = RequestMethod.GET)
    public String main(){
        return "main/chat";
    }
    
    
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message send(Message message) throws Exception {
        return message;
    }
}
