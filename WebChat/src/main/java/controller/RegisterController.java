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
import org.springframework.web.portlet.ModelAndView;
import model.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import service.UserService;
import util.HashUtil;

/**
 *
 * @author Kristoffer
 */
@Controller
public class RegisterController {
    
    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("registration", new User());
        
        return "registration";
    }
   
   
   @PostMapping("/registration")
   public String registerUser (@ModelAttribute User user) {
        //This method needs validator.
        UserService service = new UserService();
        System.out.println(user.getUsername());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        byte[] salt = HashUtil.getNewSalt();
        user.setSalt(salt);
        user.setPassword(HashUtil.hashPassword(user.getPassword(), salt));
        
        if(user.getEmail() != null){
            return "login";
        }
        else{
            return "registration";
        }
   }
}
