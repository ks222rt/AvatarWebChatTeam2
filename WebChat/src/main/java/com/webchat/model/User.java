/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.model;

/**
 *
 * @author Filip
 * 
 * The minimal requirement for an user 14-11-2016
 */
public class User {
    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private byte[] salt;
    
    public void setID(int id){
        this.id = id;
    }
    
    public int getID(){
        return id;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getUsername(){
        return username;
    }
    
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    
    public String getFirstname(){
        return firstname;
    }
    
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    
    public String getLastname(){
        return lastname;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setSalt(byte[] salt){
        this.salt = salt;
    }
    
    public byte[] getSalt(){
        return salt;
    }
    
    
    
    
}
