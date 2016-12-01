/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.controller;

import com.webchat.model.ChatMessage;
import com.webchat.model.ChatRoom;
import com.webchat.model.Message;
import com.webchat.model.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.webchat.service.ChatService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author filip
 */
@Controller
public class ChatController {
    
    @Autowired
    private ChatService chatService;
    
    @RequestMapping(value = "/main/chat", method = RequestMethod.GET)
    public String main(HttpServletRequest request, ModelMap model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("username", user.getUsername());
  
        return "main/chat";
    }
    
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message chatMessage(Message message){
       
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        return new Message(message.getFrom(),message.getText(),time);
    }
    
}
