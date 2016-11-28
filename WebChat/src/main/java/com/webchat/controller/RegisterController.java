/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.webchat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.webchat.service.UserService;
import com.webchat.util.HashUtil;
import com.webchat.validator.UserValidator;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Kristoffer
 */
@Controller
@RequestMapping("/registration")
public class RegisterController {

    @Autowired
    private UserService userService;
    private UserValidator validator;

    public RegisterController() {
        validator = new UserValidator();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String registration() {
        return "registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registerUser(@ModelAttribute User user, 
                                String password_two,
                                ModelMap model,
                                RedirectAttributes redirectAttributes) {
        /* validate the field in the user object */
        String errorMessage = validator.validateRegisterAttempt(user, password_two);
        if (errorMessage == null) { // no errors detected in users input, continue registration
            if (userService.userExists(user.getUsername())) {
                errorMessage = "Username already registered";
            } else {
                byte[] salt = HashUtil.getNewSalt();
                user.setSalt(salt);
                user.setPassword(HashUtil.hashPassword(user.getPassword(), salt));

                if (userService.registerUser(user)) {
                    redirectAttributes.addFlashAttribute("success_message", "Account successfully created!");
                    model.addAttribute("user", user);
                    return "redirect:/login.htm";
                }
            }
        }

        // TODO: If we get here, validation failed and "errorMessage" contains 
        // a string that describes the reason why. Return this string to the 
        // view
        redirectAttributes.addFlashAttribute("error_message", errorMessage);
        model.addAttribute("user", user);
        return "redirect:/registration.htm";
    }
}
