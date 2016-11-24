/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.controller;

import com.webchat.model.User;
import com.webchat.service.UserService;
import java.io.Console;
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
import org.springframework.web.bind.annotation.SessionAttributes;


/**
 *
 * @author sundi
 */


@Controller
@SessionAttributes("friends")
@RequestMapping("/main")
public class MainController {
    @Autowired
    private UserService userService;
    
    
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String main(HttpServletRequest request, ModelMap model){
        User user = (User) request.getSession().getAttribute("user");
        List<User> friendList = userService.getUserFriends(user.getId());
        model.addAttribute("friends", friendList);
            
        return "main/welcome";
    }
    
    @RequestMapping(value="/logout" , method = RequestMethod.GET)
    public String logout(HttpSession session, ModelMap model){
        model.remove("user");
        model.remove("friends");
        session.removeAttribute("user");
        return "redirect:/login.htm";
    }
    
    @RequestMapping(value="/search", method = RequestMethod.GET)
    public String serach(HttpSession session, ModelMap model){
        List<User> users = userService.getUserCollection();
        model.addAttribute("users", users);
        return "main/search";
    }
    
    @RequestMapping(value="/friendRequest/{id}", method = RequestMethod.GET)
    public String sendFriendRequest(HttpServletRequest request, @PathVariable int id, ModelMap model){
        User user = (User) request.getSession().getAttribute("user");
        if(userService.addFriendRequest(user.getId(), id)){
            return "main/welcome";
        }
        return "main/welcome";
    }
    
    @RequestMapping(value="/friendResponse/{response}/{id}", method = RequestMethod.GET)
    public String respondFriendRequest(HttpServletRequest request,@PathVariable boolean response, @PathVariable int id){
        User user = (User) request.getSession().getAttribute("user");
        
        userService.respondToFriendRequest(user.getId(), id, response);
      
        return "main/welcome";
   
    }
    
    @RequestMapping(value="/user/{username}", method = RequestMethod.GET)
    public String getUserPage(HttpServletRequest request,@PathVariable String username, ModelMap model){
        User user = userService.getUserByUsername(username);
        System.out.print("WAAAHSHASHASHSAHSAHAS");
        model.addAttribute("user", user);
        return "main/userpage";
    }
    
    @RequestMapping(value="/removeFriend/{id}", method = RequestMethod.GET)
    public String removeFriend(HttpServletRequest request,@PathVariable int id, ModelMap model){
        User user = (User) request.getSession().getAttribute("user");
        userService.removeFriend(user.getId(), id);
        return "main/welcome";
    }
}

