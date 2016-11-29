/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.controller;

import com.webchat.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author filip
 */
@Controller
public class RedirectController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String redirect(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user != null){
            return "redirect:/main/welcome";
        }
        return "redirect:/login";
    }
    
    
}
