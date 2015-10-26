package com.vaibhav.zookeeper.dynamic_spring_configuration.vo;

import org.springframework.core.env.PropertySource;

import com.netflix.config.DynamicPropertyFactory;


public class SpringArchaiusPropertySource extends PropertySource<Void>{

    public SpringArchaiusPropertySource(String name) {
        super(name);
    }

    @Override
    public Object getProperty(String name) {
        return DynamicPropertyFactory.getInstance().getStringProperty(name, null).get();
    }
}
