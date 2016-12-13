/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.config;



import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 *
 * @author Stoffe
 */
@Configuration
@ComponentScan(basePackages = { "com.webchat"}) 
@EnableWebMvc
public class ServletConfig extends WebMvcConfigurerAdapter implements ServletContextListener  {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/chat");//.allowedOrigins("https://avatar-web-chat.herokuapp.com/");
//    }
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SpringTemplateEngine engine = templateEngine(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
    @Bean
    public ServletContextTemplateResolver templateResolver(ServletContext servletContext){
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(servletContext);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        return resolver;
    }
    
    @Bean
    public SpringTemplateEngine templateEngine(ServletContext servletContext){
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver(servletContext));
        engine.addDialect(new LayoutDialect());
        return engine;
    }
    
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(ServletContext servletContext){
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine(servletContext));
        return resolver;
    }
    
    @Bean
    public MultipartResolver multipartResolver()
    {
        return new StandardServletMultipartResolver();
    }
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/styles/**").addResourceLocations("/resources/CSS/").setCachePeriod(Integer.MAX_VALUE);
        registry.addResourceHandler("/scripts/**").addResourceLocations("/resources/SCRIPT/").setCachePeriod(Integer.MAX_VALUE);
        registry.addResourceHandler("/avatars/**").addResourceLocations("/resources/AVATAR/").setCachePeriod(Integer.MAX_VALUE);
        registry.addResourceHandler("/adds/**").addResourceLocations("/resources/ADDS/").setCachePeriod(Integer.MAX_VALUE);
        registry.addResourceHandler("/autocomplete/**").addResourceLocations("/resources/autoComplete/").setCachePeriod(Integer.MAX_VALUE);
    }
    
}
