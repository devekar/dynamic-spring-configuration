package com.vaibhav.zookeeper.dynamic_spring_configuration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/")
public class ZKController {

    @Autowired
    private Environment environment;
    
    @RequestMapping(value="/{property}", method = RequestMethod.GET)
    public String getProperty(@PathVariable String property) {
        return environment.getRequiredProperty(property); 
    }
    
}
