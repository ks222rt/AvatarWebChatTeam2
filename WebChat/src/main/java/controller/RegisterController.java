/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import service.UserService;
import util.HashUtil;

/**
 *
 * @author Kristoffer
 */
@Controller
@RequestMapping("/registration")
public class RegisterController {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String registration(Model model){
        model.addAttribute("registration", new User());
        
        return "registration";
    }
   
   
   @RequestMapping(method = RequestMethod.POST)
   public String registerUser (@ModelAttribute User user) {
        //This method needs validator.
        
        byte[] salt = HashUtil.getNewSalt();
        user.setSalt(salt);
        user.setPassword(HashUtil.hashPassword(user.getPassword(), salt));
        
         if(userService.registerUser(user)){
            return "login";
        }
        else{
            return "registration";
        }
   }
}
