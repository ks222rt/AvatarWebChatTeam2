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
public class OutputMessage {
    
    private String from;
    private String text;
    private String time;
    
    public OutputMessage(String f,String te,String ti){
        this.from = f;
        this.text = te;
        this.time = ti;             
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
