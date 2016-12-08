/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.model;

/**
 *
 * @author Stoffe
 */
public class RoomHandler {
    private String command;
    
    public RoomHandler(String command) {
        this.command = command;
    }
    
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

 
}
