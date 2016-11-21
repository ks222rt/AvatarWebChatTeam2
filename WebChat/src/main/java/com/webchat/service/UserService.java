/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.service;
import com.webchat.dao.UserDAO;
import com.webchat.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
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
    
}
