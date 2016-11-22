/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.controller;

import com.webchat.model.User;
import com.webchat.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *
 * @author sundi
 */



@RequestMapping("/main")
@Controller
public class MainController {
    @Autowired
    private UserService userService;
    
    
    @RequestMapping(method = RequestMethod.GET)
    public String main(ModelMap model){
       
        List<User> users = userService.getUserCollection();

        model.addAttribute("users", users);
        
        for (User userVar : users) {
            System.out.println(userVar.getUsername());
        }
        
        return "main";
    }
    
    @RequestMapping(value="/logout" , method = RequestMethod.GET)
    public String logout(HttpSession session, ModelMap model){
        model.remove("user");
        session.removeAttribute("user");
        return "redirect:/login.htm";
    }
    
    @RequestMapping(value="/friendRequest/{id}", method = RequestMethod.GET)
    public String sendFriendRequest(HttpServletRequest request, @PathVariable int id, ModelMap model){
        User user = (User) request.getSession().getAttribute("user");
        throw new UnsupportedOperationException("Not yet implemented");
        
       /*TODO: user.getID() and id should be sent to 
        *      a service eg. userService.friendRequest(sender,target)
        */
    }
    
    @RequestMapping(value="/friendAccept/{id}", method = RequestMethod.GET)
    public String acceptFriendRequest(HttpServletRequest request, @PathVariable int id, ModelMap model){
        User user = (User) request.getSession().getAttribute("user");
        throw new UnsupportedOperationException("Not yet implemented");
        
        /*TODO: user.getID() and id should be sent to 
         *      a service eg. userService.acceptFriend(sender,target)
         */
    }
}

