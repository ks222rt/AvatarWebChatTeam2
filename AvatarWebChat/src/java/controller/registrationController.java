/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.ModelAndView;

/**
 *
 * @author Kristoffer
 */
@Controller
@RequestMapping("/registration")
public class registrationController {
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView mav = new ModelAndView("registration");
        
        return mav;
    }
}
