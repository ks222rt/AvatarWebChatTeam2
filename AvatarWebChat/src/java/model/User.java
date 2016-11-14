/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Filip
 * 
 * The minimal requirement for an user 14-11-2016
 */
public class User {
    private int _id;
    private String _username;
    private String _firstname;
    private String _lastname;
    private String _email;
    private String _password;
    private byte[] _salt;
    
    public void setID(int id){
        _id = id;
    }
    
    public int getID(){
        return _id;
    }
    
    public void setUsername(String username){
        _username = username;
    }
    
    public String getUsername(){
        return _username;
    }
    
    public void setFirstname(String firstname){
        _firstname = firstname;
    }
    
    public String getFirstname(){
        return _firstname;
    }
    
    public void setLastname(String lastname){
        _lastname = lastname;
    }
    
    public String getLastname(){
        return _lastname;
    }
    
    public void setEmail(String email){
        _email = email;
    }
    
    public String getEmail(){
        return _email;
    }
    
    public void setPassword(String password){
        _password = password;
    }
    
    public String getPassword(){
        return _password;
    }
    
    public void setSalt(byte[] salt){
        _salt = salt;
    }
    
    public byte[] getSalt(){
        return _salt;
    }
    
    
    
    
}
