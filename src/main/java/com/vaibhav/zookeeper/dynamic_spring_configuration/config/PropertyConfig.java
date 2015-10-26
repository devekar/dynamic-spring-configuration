package com.vaibhav.zookeeper.dynamic_spring_configuration.config;

import javax.annotation.PostConstruct;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicWatchedConfiguration;
import com.netflix.config.source.ZooKeeperConfigurationSource;
import com.vaibhav.zookeeper.dynamic_spring_configuration.vo.SpringArchaiusPropertySource;

@Configuration
public class PropertyConfig {

    @Autowired
    Environment environment;
    @Autowired
    ConfigurableApplicationContext ctx;
    @Autowired
    CuratorFramework curatorFrameworkClient;
    
    @PostConstruct
    public void initialize() {
        String zkConfigRootPath = environment.getProperty("zkConfigRootPath");
        
        ZooKeeperConfigurationSource zkConfigSource = new ZooKeeperConfigurationSource(curatorFrameworkClient, zkConfigRootPath);
        try {
            zkConfigSource.start();
            DynamicWatchedConfiguration zkDynamicConfig = new DynamicWatchedConfiguration(zkConfigSource);
            ConfigurationManager.install(zkDynamicConfig);
            
            PropertySource<?> ps = new SpringArchaiusPropertySource(zkConfigRootPath);
            ctx.getEnvironment().getPropertySources().addFirst(ps);
        } catch (Exception e) {
            System.out.println("Failed to load config from zookeeper!");
        }
        
        System.out.println("Registered zookeeper properySource in Spring!");
    }
    
    

}
