/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.service;
import com.webchat.dao.UserDAO;
import com.webchat.model.ChatRoom;
import com.webchat.model.ChatUserHelper;
import com.webchat.model.Message;
import com.webchat.model.User;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author Filip
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
        
    public boolean registerUser(User user){
        return userDAO.addUser(user);
    }
    
    public User loginUser(String username, String password){
        return userDAO.loginUser(username, password);
    }

    public List<User> getUserCollection(){
        return userDAO.getAllUsers();

    }
    
    public boolean userExists(String username){
        if (userDAO.searchUser(username) == null)
        {
            return false;
        }
        return true;
    }
    
    public User getUserByUsername(String username){
        return userDAO.searchUser(username);
    }
    
    public boolean addFriendRequest(int senderID, int recieverID){
        return userDAO.addFriendRequest(senderID, recieverID );
       
    }
    
    public boolean respondToFriendRequest(int senderID, int recieverID, boolean accepted){
        
        if(userDAO.respondToFriendRequest(senderID, recieverID, accepted))
        {   
           return userDAO.createPrivateChat(senderID, recieverID);
        }
        return false;
    }

    public List<User> getUserFriends(int userID) {
        
       return userDAO.getUserFriends(userID);
    }
    
    public boolean removeFriend(int senderID, int recieverID) {
        
        return userDAO.removeFriend(senderID, recieverID);
    }
    
    public List<User> getUserFriendRequests(int userID) {
            
        return userDAO.getFriendRequests(userID);       
    }

    public boolean updateUserPassword(User user) {
        return userDAO.updateUserPassword(user);
    }

    public boolean deleteAccount(User user) {
        return userDAO.deleteAccount(user);
    }
    
    public boolean areWeFriends(int userID, int friendID){
        return userDAO.alreadyFriends(userID, friendID);
    }

    public boolean reportUser(int id, int userId, String answer) {
        return userDAO.reportUser(id, userId, answer);
    }

    public boolean isMyAccountDisabled(int id) {
        return userDAO.isMyAccountDisabled(id);
    }

    public boolean updateUserSubscription(User user) {
        return userDAO.updateUserSubscription(user);
    }
   
}
