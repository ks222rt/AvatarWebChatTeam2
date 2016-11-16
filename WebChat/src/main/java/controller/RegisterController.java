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
import org.springframework.web.servlet.ModelAndView;
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
    public ModelAndView registration(Model model){
        model.addAttribute("registration", new User());
        
        return new ModelAndView("registration");
    }
   
   
   @RequestMapping(method = RequestMethod.POST)
   public ModelAndView registerUser (@ModelAttribute User user) {
        //This method needs validator.
        
        byte[] salt = HashUtil.getNewSalt();
        user.setSalt(salt);
        user.setPassword(HashUtil.hashPassword(user.getPassword(), salt));
        
         if(userService.registerUser(user)){
            return new ModelAndView("redirect:/login.htm", "user", user);
        }
        else{
            return new ModelAndView("registration");
        }
   }
}
