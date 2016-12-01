
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.model;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Adam
 */
public class ChatRoom {

  
    
    private int roomId;
    private List<ChatMessage> messages;
    private List<Map<String,Integer>> members;
    
    public ChatRoom(int roomId) {
        this.roomId = roomId;
    }
    
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public List<Map<String,Integer>> getMembers() {
        return members;
    }

    public void setMembers(List<Map<String,Integer>> members) {
        this.members = members;
    }
}
