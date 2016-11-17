/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.validator;

import com.webchat.model.User;
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

    public boolean validateLoginAttempt(String username, String password) {
        if (username == null || password == null)
        {
            return false;
        }
        
        if (unamePattern.matcher(username).find())
        {
            return false;
        }
        
        if (password.length() < 8)
        {
            return false;
        }
        
        return true;
    }

    public boolean validateRegisterAttempt(User user) {
        Set<ConstraintViolation<User>> violations = validator.validateProperty(
                user, "username");

        // TODO: instead of returning false we should return something that 
        // can be used to indicate the incorrect fields on the registration page
        if (violations.size() != 0 || unamePattern.matcher(user.getUsername()).find()) // incorrect username
        {
            return false;
        }

        violations = validator.validateProperty(user, "firstname");
        if (violations.size() != 0) // incorrect first name
        {
            return false;
        }

        violations = validator.validateProperty(user, "lastname");
        if (violations.size() != 0) // incorrect last name
        {
            return false;
        }

        violations = validator.validateProperty(user, "email");
        if (violations.size() != 0) // incorrect e-mail address
        {
            return false;
        }

        return true;
    }
}
