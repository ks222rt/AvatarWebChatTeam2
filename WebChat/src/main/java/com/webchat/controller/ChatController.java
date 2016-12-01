/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.controller;

import com.webchat.model.ChatRoom;
import com.webchat.model.ChatUserHelper;
import com.webchat.model.Message;
import com.webchat.model.User;
import com.webchat.service.UserService;
import com.webchat.util.SessionUtil;
import groovy.lang.MissingPropertyException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *
 * @author filip
 */

@Controller
public class ChatController {

    @Autowired
    private UserService userService;
    @Autowired
    private SessionUtil sessionUtil;
    List<ChatRoom> chatRoomAndFriendIds;
    
    @RequestMapping(value = "/main/chat/none", method = RequestMethod.GET)
    public String main(HttpServletRequest request, ModelMap model){
        User user = (User) request.getSession().getAttribute("user");
        chatRoomAndFriendIds = userService.getRoomsForUser(user.getId());
        
        model.addAttribute("username", user.getUsername());
        return "main/chat";
    }
    
    @RequestMapping(value = "/main/chat/{roomId}", method = RequestMethod.GET)
    public String chatWithUser(HttpServletRequest request, ModelMap model, @PathVariable int roomId){
        User user = (User) request.getSession().getAttribute("user");
        chatRoomAndFriendIds = userService.getRoomsForUser(user.getId());
        model.addAttribute("roomId", roomId);
        return "main/chat";
    }
    
    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}/messages")
    public Message chatMessage(@DestinationVariable int roomId, Message message){
        System.out.println("ROOMID = " + roomId);
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        return new Message(message.getFrom(),message.getText(),time);
    }
    
    @SubscribeMapping("/initChat")
    public List<ChatUserHelper> listFriends (){
        return getListOfOnlineFriends();
    } 
    
    
    private List<ChatUserHelper> getListOfOnlineFriends(){
         List<User> onlineUsers = sessionUtil.getOnlineUsers();
         List<ChatUserHelper> onlineFriends = new LinkedList<>();
         for(ChatRoom room : chatRoomAndFriendIds){
             
             for(Map<String, Integer> mep : room.getMembers()) {
                 
                 for(User user : onlineUsers){
                     if(mep.containsKey(user.getUsername())){
                         ChatUserHelper onlineUser = new ChatUserHelper(room.getRoomId(), user.getId(), user.getUsername());
                         onlineFriends.add(onlineUser);
                         break;
                     }
                 }
                 
             }
         }
         return onlineFriends;
    }
}



