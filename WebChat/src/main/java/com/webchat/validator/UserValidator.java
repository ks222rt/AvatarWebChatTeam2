/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.validator;

import com.webchat.model.User;
import com.webchat.util.HashUtil;

import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;

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
       ValidatorFactory vFactory = Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory();
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

    public String validateRegisterAttempt(User user, String password) {
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
        
        violations = validator.validateProperty(user, "password");
        if (violations.size() != 0) // incorrect e-mail address
        {
            return "Password must be atleast 8 characters long";
        }
        
        if (!user.getPassword().equals(password)) {
            return "Passwords doesn´t match";
        }
        
        if (password.length() < 8 && user.getPassword().length() < 8) {
            return "passwords must be atleast 8 characters";
        }
        
        return null;
    }
    
    public boolean validateOldPassword(User user, String password){
        return user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()));
    }
    
    public boolean validateBothNewPasswords(User user, String password1, String password2){       
        if (user.getPassword().equals(HashUtil.hashPassword(password2, user.getSalt()))
                || user.getPassword().equals(HashUtil.hashPassword(password1, user.getSalt()))) {
            return false;
        }
        
        if (!validateTwoPasswordFromForm(password1, password2)) {
            return false;
        }
        
        return true;
    }
    
    public boolean validatePasswordWhenDeletingAccount(User user, String password1, String password2){
        if (validateTwoPasswordFromForm(password1, password2)) {
            if (user.getPassword().equals(HashUtil.hashPassword(password2, user.getSalt()))
                && user.getPassword().equals(HashUtil.hashPassword(password1, user.getSalt()))) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean validateTwoPasswordFromForm(String password1, String password2){
        if (!password1.equals(password2)) {
            return false;
        }
        
        if (password1.length() < 8) {
            return false;
        }
        
        if (password2.length() < 8) {
            return false;
        }
        
        return true;
    }
}
