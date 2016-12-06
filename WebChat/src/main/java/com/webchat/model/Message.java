/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.model;

/**
 *
 * @author filip
 */
public class Message {
    private String from;
    private String text;
    private String time;
    private int user_id;
    
    public Message() {
     
    }
    
    public Message(String from, String text, String time) {
        this.from = from;
        this.text = text;
        this.time = time;
    }
    
    public Message(String from, String text, String time, int user_id) {
        this.from = from;
        this.text = text;
        this.time = time;
        this.user_id = user_id;
    }
    
    
     public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

   

}
