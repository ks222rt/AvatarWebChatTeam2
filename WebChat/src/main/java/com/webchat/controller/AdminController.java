/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.controller;

import com.webchat.model.User;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Stoffe
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public String getReportsPage(HttpServletRequest request, 
                                 ModelMap model,
                                 RedirectAttributes redirectAttributes){
        User user = (User) request.getSession().getAttribute("user");
        
        if (!user.getUsername().equalsIgnoreCase("admin")) {
            redirectAttributes.addFlashAttribute("error_message", "You dont have permission to this!!");
            return "redirect:/main/welcome";
        }
        
        
        return "admin/userReports";
    }
    
}
