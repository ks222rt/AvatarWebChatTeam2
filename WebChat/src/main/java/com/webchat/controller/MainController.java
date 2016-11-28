/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.controller;

import com.webchat.model.User;
import com.webchat.service.UserService;
import com.webchat.util.HashUtil;
import com.webchat.validator.UserValidator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
    
    private UserValidator validator;
    
    public MainController(){
        validator = new UserValidator();
    }
    
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String main(HttpServletRequest request, ModelMap model){
        User user = (User) request.getSession().getAttribute("user");
        int id = user.getId();
        List<User> friendList = userService.getUserFriends(id);
        List<User> friendRequestList = userService.getUserFriendRequests(id);
        model.addAttribute("friends", friendList);
        model.addAttribute("friendRequests", friendRequestList);
            
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
    
    @RequestMapping(value="/friends", method = RequestMethod.GET)
    public String friends(HttpServletRequest request, ModelMap model){
        User user = (User) request.getSession().getAttribute("user");
        List<User> friendList = userService.getUserFriends(user.getId());
        model.addAttribute("friendList", friendList);
        return "main/friends";
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
      
        return "redirect:/main/friendRequests.htm";
   
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
    
    @RequestMapping(value="/friendRequests", method = RequestMethod.GET)
    public String friendRequests(HttpServletRequest request, ModelMap model){
         User user = (User) request.getSession().getAttribute("user");
         System.out.println(user.getId());
         List<User> friends = userService.getUserFriendRequests(user.getId());
         
             for(User friend : friends){
                 System.out.println(friend.getUsername());
             }
         if(friends != null){
             
         
             
            model.addAttribute("friendRequests", friends);
            return "main/friendRequest";
        }
          return "redirect:/main/welcome.htm";
    }
    
    @RequestMapping(value="/settings", method = RequestMethod.GET)
    public String userSettings(HttpServletRequest request, ModelMap model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "userSettings/settings";
    }
    
    @RequestMapping(value="/settings/changePassword", method = RequestMethod.GET)
    public String changePasswordPage(HttpServletRequest request, ModelMap model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "userSettings/changePassword";
    }
    
    @RequestMapping(value="/settings/changePassword", method = RequestMethod.POST)
    public String changePassword(@RequestParam String first_password,
                                 @RequestParam String second_password,
                                 @RequestParam String old_password,
                                 HttpServletRequest request,
                                 ModelMap model,
                                 RedirectAttributes redirectAttributes){
        User user = (User) request.getSession().getAttribute("user");
        
        // Check if old password is right
        if (validator.validateOldPassword(user, old_password)) {
            // Check if new1 and new2 password is the same
            if (validator.validateBothNewPasswords(user, first_password, second_password)) {
                user.setPassword(HashUtil.hashPassword(first_password, user.getSalt()));
                if (userService.updateUserPassword(user)) {
                    redirectAttributes.addFlashAttribute("success_message", "Password is changed!");
                    return "redirect:/main/settings.htm";
                }
            }
        }
        redirectAttributes.addFlashAttribute("error_message", "Something went wrong!");
        return "redirect:/main/settings/changePassword.htm";
    }
    
    @RequestMapping(value="/settings/deleteAccount", method = RequestMethod.GET)
    public String deleteAccountPage(HttpServletRequest request, ModelMap model){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "userSettings/deleteAccount";
    }
    
    @RequestMapping(value="/settings/deleteAccount", method = RequestMethod.POST)
    public String deleteAccount(@RequestParam String password,
                                @RequestParam String password_again,
                                HttpServletRequest request,
                                ModelMap model,
                                RedirectAttributes redirectAttributes){
        
        User user = (User) request.getSession().getAttribute("user");
        
        if (validator.validatePasswordWhenDeletingAccount(user, password, password_again)) {
            // delete account
            
            // remove from friendlists
            
            // delete session
            
            // redirect to welcome
            
            redirectAttributes.addFlashAttribute("success_message", "Account was 'removed'");
            return "redirect:/main/settings.htm";
        }
        
        redirectAttributes.addFlashAttribute("error_message", "Passwords doesn not match!");
        return "redirect:/main/settings/deleteAccount.htm";
        
    }
}

