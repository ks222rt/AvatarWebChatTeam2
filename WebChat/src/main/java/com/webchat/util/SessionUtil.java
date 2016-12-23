/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.util;

import com.webchat.model.ChatRoom;
import com.webchat.model.ChatUserHelper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;

import com.webchat.model.User;
import com.webchat.service.ChatService;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.springframework.stereotype.Component;


/**
 *
 * @author Stoffe
 */
@Component
public class SessionUtil {
    
    @Autowired
    private SessionRegistry sessionRegistry;
    
    @Autowired
    private ChatService chatService;
    
    HashMap<Integer,ArrayDeque<ChatRoom>> listOfChatRoomsPerUser = new HashMap<>();
    
    public void registerNewSession(User user){
        sessionRegistry.registerNewSession(user.getUsername(), user);
    }
    
    public void removeSession(String username){
        sessionRegistry.removeSessionInformation(username);
    }
    
    public List<User> getOnlineUsers(){
        List<User> listOfUsers = new LinkedList<>();
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof User) {
                listOfUsers.add((User) principal);
            }
        }
        return listOfUsers;
    }
    
    public void updateChatRoomsByUserId(int userId){
        listOfChatRoomsPerUser.put(userId, chatService.getRoomsForUser(userId));
    }
    
    public ArrayDeque<ChatRoom> getChatRoomsByUserId(int userId){
         return listOfChatRoomsPerUser.get(userId);
    }
}
