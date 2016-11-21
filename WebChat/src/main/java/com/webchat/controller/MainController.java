/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.controller;

import com.webchat.model.User;
import com.webchat.service.UserService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.ModelAndView;

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
        
        for (User user : users) {
            System.out.println(user.getUsername());
        }
        
        return "main";
    }
    
    @RequestMapping( value="/logout" , method = RequestMethod.GET)
    public String logout(HttpSession session, ModelMap model){
        model.remove("user");
        session.removeAttribute("user");
        return "redirect:/login.htm";
    }
}

