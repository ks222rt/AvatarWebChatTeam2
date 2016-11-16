/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import service.UserService;

/**
 *
 * @author Kristoffer
 */

@Controller
@RequestMapping("/login")
public class LoginController {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView login(){
        return new ModelAndView("login");      
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String doLogin(@RequestParam String username, @RequestParam String password){        
        if (userService.loginUser(username, password)) {
            //return new ModelAndView("redirect:/main.htm");
            redirectAttrs.addAttribute("username", username).addFlashAttribute("message", "You are nog logged in!");
            return "redirect:/main.htm";
        }else{
            //return new ModelAndView("redirect:/login.htm");
            return "login";
        }
    }
}
