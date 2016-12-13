/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.controller;

import com.webchat.model.User;
import com.webchat.model.UserReportHelper;
import com.webchat.service.AdminService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @Autowired
    private AdminService adminService;
    
    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public String getReportsPage(HttpServletRequest request, 
                                 ModelMap model,
                                 RedirectAttributes redirectAttributes){
        User user = (User) request.getSession().getAttribute("user");
        
        if (user.getIsAdmin() == 0) {
            redirectAttributes.addFlashAttribute("error_message", "You dont have permission to that!!");
            return "redirect:/main/welcome";
        }
        
        // Next to do is to get all reported users
        List<UserReportHelper> allReportedUsers = adminService.getListOfReports();
        
        // Add them to the model
        model.addAttribute("reportedUsers", allReportedUsers);
        
        //Return the report page with the list of users       
        return "admin/userReports";
    }
    
    @RequestMapping(value = "/disableUser/{id}", method = RequestMethod.GET)
    public String disableUser(HttpServletRequest request,
                              RedirectAttributes redirectAttributes,
                              @PathVariable int id){
        User user = (User) request.getSession().getAttribute("user");
        
        if (user.getIsAdmin() == 0) {
            redirectAttributes.addFlashAttribute("error_message", "You dont have permission to that!!");
            return "redirect:/main/welcome";
        }
        
        // Send request to the DAO class to disable user and take it away from the list with reports
        if (adminService.disableUser(id)) {
            // return to admin page with suitable message
            redirectAttributes.addFlashAttribute("success_message", "Account was disabled");
            return "redirect:/admin/reports";
        }else{
            // return to admin page with suitable message
            redirectAttributes.addFlashAttribute("error_message", "Something went wrong with the request");
            return "redirect:/admin/reports";
        }
       
    }
    
    
    @RequestMapping(value = "/removeReport/{id}", method = RequestMethod.GET)
    public String removeReportFromUser(HttpServletRequest request,
                                       RedirectAttributes redirectAttributes,
                                       @PathVariable int id){
        User user = (User) request.getSession().getAttribute("user");
        
        if (user.getIsAdmin() == 0) {
            redirectAttributes.addFlashAttribute("error_message", "You dont have permission to that!!");
            return "redirect:/main/welcome";
        }
        
        // Send request to DAO class to remove report on the user and DONT disable hen
        if (adminService.removeReportFromUser(id)) {
            // return to admin page with suitable message
            redirectAttributes.addFlashAttribute("success_message", "Report was removed from user");
            return "redirect:/admin/reports";
        }else{
            // return to admin page with suitable message
            redirectAttributes.addFlashAttribute("error_message", "Something went wrong with the request");
            return "redirect:/admin/reports";
        }
    }
    
    
}
