/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.validator;

import com.webchat.model.User;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author filip
 */
public class UserValidator {

    /* A Validator instance is thread safe and can be used multiple times... */
    private static Validator validator;
    
    /* This pattern should contain all valid characters in a user name */
    private Pattern unamePattern;
   
    public UserValidator() {
        ValidatorFactory vFactory = Validation.buildDefaultValidatorFactory();
        validator = vFactory.getValidator();
        unamePattern = Pattern.compile("[^a-zA-Z0-9]");
    }

    public HashMap validateLoginAttempt(String username, String password) {
        HashMap<String, String> hashMap = new HashMap<>();
        if (username.length() == 0 || password.length() == 0)
        {
            hashMap.put("error", "Username and password cant be empty!");
            return hashMap;
        }
        
        if (unamePattern.matcher(username).find())
        {
            hashMap.put("error", "Illegal characters in username");
            return hashMap;
        }
        
        if (password.length() < 8)
        {
            hashMap.put("error", "Password must be atleast 8 characters");
            return hashMap;
        }
        
        hashMap.put("success", "Validation OK!");
        return hashMap;
    }

    public String validateRegisterAttempt(User user) {
        Set<ConstraintViolation<User>> violations = validator.validateProperty(
                user, "username");

        // TODO: instead of returning false we should return something that 
        // can be used to indicate the incorrect fields on the registration page
        if (violations.size() != 0 || unamePattern.matcher(user.getUsername()).find()) // incorrect username
        {
            return "A valid username is 4 to 20 characters long and made up of the letters A - Z (a - z) and the numbers 0 - 9";
            
        }

        violations = validator.validateProperty(user, "firstname");
        if (violations.size() != 0) // incorrect first name
        {
            return "Incorrect first name";
        }

        violations = validator.validateProperty(user, "lastname");
        if (violations.size() != 0) // incorrect last name
        {
            return "Incorrect last name";
        }

        violations = validator.validateProperty(user, "email");
        if (violations.size() != 0) // incorrect e-mail address
        {
            return "Incorrect e-mail address";
        }
        
        // validation ok
        return null;
    }
}
