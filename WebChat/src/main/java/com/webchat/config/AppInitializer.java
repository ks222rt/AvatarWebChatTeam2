/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.config;

import static com.webchat.config.SecurityConfig.httpSessionEventPublisher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author Stoffe
 */
public class AppInitializer implements WebApplicationInitializer{

    @Override
    public void onStartup(ServletContext context) throws ServletException {
        
        AnnotationConfigWebApplicationContext appContext = 
                new AnnotationConfigWebApplicationContext();
        
        appContext.setDisplayName("Avatar Web Chat");
        appContext.register(AppContext.class);
        appContext.register(SecurityConfig.class);
       
        context.addListener(httpSessionEventPublisher());
        context.addListener(new ContextLoaderListener(appContext));
        
        AnnotationConfigWebApplicationContext servletContext = 
                new AnnotationConfigWebApplicationContext();
        
        servletContext.register(ServletConfig.class);
        //servletContext.register(SecurityConfig.class);
        
        ServletRegistration.Dynamic dispatcher =
                context.addServlet("dispatcher", new DispatcherServlet(servletContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
    
}
