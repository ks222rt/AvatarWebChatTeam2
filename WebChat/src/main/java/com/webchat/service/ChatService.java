/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.service;

import com.webchat.dao.ChatDAO;
import com.webchat.model.ChatRoom;
import com.webchat.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author Adam
 */
@Service
public class ChatService {
    
    @Autowired
    private ChatDAO chatDAO;
    
    public List<ChatRoom> getUserChatRooms(User user) {
        
        return chatDAO.getUserChatRooms(user);
        
    }
    
    public boolean addChatRoom(ChatRoom chatRoom) {
        
        return chatDAO.addChatRoom(chatRoom);
    }
    
    public ChatRoom getChatRoomWithData(ChatRoom chatRoom, User user) { 
        
        return chatDAO.getChatRoomWithData(chatRoom, user);
    }
    
}
