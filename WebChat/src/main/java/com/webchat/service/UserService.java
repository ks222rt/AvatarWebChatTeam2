/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.service;
import com.webchat.dao.UserDAO;
import com.webchat.model.ChatRoom;
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
            List<Integer> userIds = new ArrayList<>();
            userIds.add(senderID);
            userIds.add(recieverID);
            return userDAO.createRoom(userIds, 0); //the zero defines if its group chat or not (0 is false.)
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
    
    public List<ChatRoom> getRoomsForUser(int userId){
        return userDAO.getChatRooms(userId);
    }
    
    public boolean createGroupChat(List<Integer> userIds){
        if(userIds.size() > 2){
            return userDAO.createRoom(userIds, 1);//1 defines if its group chat or not (1 is true.)
        }
        return false;
    }
    
    public boolean addUserToGroup(int userId, int roomId){
        return userDAO.insertUserToChatRoom(userId, roomId);
    }
}
