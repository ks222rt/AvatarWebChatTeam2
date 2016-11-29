/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.model;

import java.util.Map;

/**
 *
 * @author Adam
 */
public class ChatMessage {
    
    private User sender;
    private Map<User, Boolean> messageRecieverMap; //User object linked to boolean value referencing if user has deleted message history.
    private String message;
    
    
}
