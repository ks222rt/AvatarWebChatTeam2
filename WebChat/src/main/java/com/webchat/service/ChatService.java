/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.service;

import com.webchat.dao.ChatDAO;
import com.webchat.model.ChatRoom;
import com.webchat.model.ChatUserHelper;
import com.webchat.model.Message;
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

    public boolean createGroupChat(String roomName, int roomId, int newUser){
        return chatDAO.createGroup(roomName, roomId, newUser);
    }

    public boolean createPrivateChat(String roomName, int userId1, int userId2) {         
        return chatDAO.createPrivateChat(roomName, userId1, userId2);
    }

    public boolean addMessageToRoom(Message message, int roomId){
        return chatDAO.addMessageToRoom(message, roomId);
    }
    
    public List<ChatRoom> getRoomsForUser(int userId){
        return chatDAO.getChatRooms(userId);
    }
    
    public List<ChatUserHelper> getUsersinRoom(int roomId, int userId){
        return chatDAO.getUsersInRoom(roomId, userId);
    }
    
    public List<Message> getMessagesByRoomId(int roomId){
        return chatDAO.getMessagesInRoom(roomId);
    }
}
