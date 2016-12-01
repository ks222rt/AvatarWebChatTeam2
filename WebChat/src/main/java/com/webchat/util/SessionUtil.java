/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.util;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;

import com.webchat.model.User;
import java.util.LinkedList;
import org.springframework.stereotype.Component;


/**
 *
 * @author Stoffe
 */
@Component
public class SessionUtil {
    
    @Autowired
    private SessionRegistry sessionRegistry;
    
    public void registerNewSession(User user){
        sessionRegistry.registerNewSession(user.getUsername(), user);
    }
    
    public void removeSession(String username){
        sessionRegistry.removeSessionInformation(username);
    }
    
    public List<User> getOnlineUsers(){
        List<User> listOfUsers = new LinkedList<>();
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof User) {
                listOfUsers.add((User) principal);
            }
        }
        return listOfUsers;
    }
}
