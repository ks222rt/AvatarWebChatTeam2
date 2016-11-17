/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.validator;

import com.webchat.model.User;
import java.util.Set;
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
    
    public UserValidator() {
        ValidatorFactory vFactory = Validation.buildDefaultValidatorFactory();
        validator = vFactory.getValidator();
    }
    public boolean validateLoginAttempt(String username, String password){
        throw new UnsupportedOperationException();
    }
    
    public boolean validateRegisterAttempt(User user){
        Set<ConstraintViolation<User>> violations = validator.validateProperty(
                user, "username");
        
        // TODO: instead of returning false we should return something that 
        // can be used to indicate the incorrect fields on the registration page
        if (violations.size() != 0) // incorrect username
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
