/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.model;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 *
 * @author Adam
 */
public class ChatMessage {
    
    private String sender;
    private String message;
    private SimpleDateFormat timeStamp;
    
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public SimpleDateFormat getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(SimpleDateFormat timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    
}
