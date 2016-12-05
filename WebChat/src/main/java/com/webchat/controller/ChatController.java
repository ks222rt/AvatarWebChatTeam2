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
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
    
    //@RequestMapping(value = "/main/chat/createRoom/{roomId}", method = RequestMethod.GET)
    //public String createGroup()
    
    
    
    @RequestMapping(value = "/main/chat/{roomId}", method = RequestMethod.GET)
    public String chatWithUser(HttpServletRequest request, ModelMap model, @PathVariable int roomId){
        User user = (User) request.getSession().getAttribute("user");
        chatRoomAndFriendIds = userService.getRoomsForUser(user.getId());
        
        if(hasAccessToRoom(roomId)){    
            model.addAttribute("roomId", roomId);
            return "main/chat";
        }
        
        return "redirect:/main/chat/none";
        
    }
    
    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}/messages")
    public Message chatMessage(@DestinationVariable int roomId, Message message){
        System.out.println("ROOMID = " + roomId);
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        return new Message(message.getFrom(),message.getText(),time);
    }
    
    @SubscribeMapping("/initChat")
    public List<ChatRoom> listFriends (){
        return getListOfOnlineFriends();
    } 
    
    
    private List<ChatRoom> getListOfOnlineFriends(){
         List<User> onlineUsers = sessionUtil.getOnlineUsers();
         HashSet<String> onlineUsernames = new HashSet<>();
         for(User u : onlineUsers) { 
             onlineUsernames.add(u.getUsername());            
         }
         System.out.println("Online Users:" + onlineUsers.size());
         List<ChatRoom> roomsToBeReturned = new ArrayList<>();
         
         
        for(ChatRoom room : chatRoomAndFriendIds){
            List<ChatUserHelper> listOfUsersInRoom = room.getMembers();
            if(room.getIsGroupRoom() == 0){
               for(ChatUserHelper user : listOfUsersInRoom){
                    if(onlineUsernames.contains(user.getUsername())){
                       roomsToBeReturned.add(room);
                    }     
               }
            }else{
                roomsToBeReturned.add(room);
            }
        }
        return roomsToBeReturned;
    }
    
        private boolean hasAccessToRoom(int roomId){
            for(ChatRoom room : chatRoomAndFriendIds){

                if(room.getRoomId() == roomId){
                    return true;
                }
            }
            return false;
        }
    
}



