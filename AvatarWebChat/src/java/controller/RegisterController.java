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
import service.UserService;
import util.HashUtil;

/**
 *
 * @author Kristoffer
 */
@Controller
@RequestMapping("/registration")
public class RegisterController {
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView mav = new ModelAndView("registration");
        
        return mav;
    }
   
   
   @RequestMapping(value = "/registration", method = RequestMethod.POST)
   public String registerUser (@ModelAttribute("form")User user) {
        //This method needs validator.
        UserService service = new UserService();
        
        byte[] salt = HashUtil.getNewSalt();
        user.setSalt(salt);
        user.setPassword(HashUtil.hashPassword(user.getPassword(), salt));
        
        if(service.registerUser(user)){
            return "login";
        }
        else{
            return "registration";
        }
   }
}
