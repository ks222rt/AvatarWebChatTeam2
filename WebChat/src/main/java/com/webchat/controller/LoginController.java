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
import com.webchat.util.SessionUtil;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.webchat.validator.UserValidator;
import java.util.HashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Kristoffer
 */

@Controller
@SessionAttributes({"user","chatRooms"})
@RequestMapping("/login")
public class LoginController {
    
    @Autowired
    private UserService userService;
    private UserValidator validator;
    
    @Autowired
    private SessionUtil sessionUtil;
        
    public LoginController() {
        validator = new UserValidator();
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String login(){
        return "login";      
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          ModelMap model,
                          RedirectAttributes redirectAttributes){ 
        
        /* verify the format of the user name and password */
        HashMap validationResult = validator.validateLoginAttempt(username, password);
        if (validationResult.containsKey("error"))
        {
            redirectAttributes.addFlashAttribute("error_message", validationResult.get("error"));
            return "redirect:/login";
        }
        
        User user = userService.loginUser(username, password);
        
        if(user != null){
            if (userService.isMyAccountDisabled(user.getId())) {
                redirectAttributes.addFlashAttribute("error_message", "Your account is disabled");
                return "redirect:/login";
            }
            sessionUtil.updateChatRoomsByUserId(user.getId());
            sessionUtil.registerNewSession(user);
            model.addAttribute("user", user);
            return "redirect:/main/welcome";
        } else {
            redirectAttributes.addFlashAttribute("error_message", "Wrong username/password");
            return "redirect:/login";
        }
    }
}
