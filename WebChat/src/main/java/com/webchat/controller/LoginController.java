/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.controller;

import com.webchat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import com.webchat.service.UserService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author Kristoffer
 */

@Controller
@SessionAttributes("user")
@RequestMapping("/login")
public class LoginController {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView login(){
        return new ModelAndView("login");      
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          ModelMap model){ 
        
        User user = userService.loginUser(username, password);
        
        if(user != null){
            model.put("user", user);
            return "redirect:/main.htm";
        } else {
            return "login";
        }
    }
}
