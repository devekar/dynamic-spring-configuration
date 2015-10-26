package com.vaibhav.zookeeper.dynamic_spring_configuration.config;

import javax.annotation.PostConstruct;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
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

    /**
     * Integrate Zookeeper Config source post context-initialization
     */
    //TODO: Is there a better approach to load zookeeper data in the application lifecycle?
    @PostConstruct
    public void initialize() {
        String zkConfigRootPath = environment.getProperty("zkConfigRootPath");

        try {
            initializeArchaiusConfigurationManager(zkConfigRootPath);
            integrateArchaiusIntoSpringEnvironment(zkConfigRootPath);
        } catch (Exception e) {
            System.out.println("Failed to load config from zookeeper!: " + e.getStackTrace());
        }

        System.out.println("Registered zookeeper properySource in Spring!");
    }

    /**
     * Configure Archaius to use Zookeeper as config source
     * @param zkConfigRootPath Path to node designated for application
     * @throws Exception
     */
    private void initializeArchaiusConfigurationManager(String zkConfigRootPath) throws Exception {
        ZooKeeperConfigurationSource zkConfigSource = new ZooKeeperConfigurationSource(curatorFrameworkClient, zkConfigRootPath);
        zkConfigSource.start();
        DynamicWatchedConfiguration zkDynamicConfig = new DynamicWatchedConfiguration(zkConfigSource);
        ConfigurationManager.install(zkDynamicConfig);        
    }


    /**
     * Add custom PopertySource implementation for Archaius into context 
     * so that zookeeper-backed properties are visible in spring environment.
     * @param sourceName Name for the PopertySource
     */
    private void integrateArchaiusIntoSpringEnvironment(String sourceName) {
        PropertySource<?> ps = new SpringArchaiusPropertySource(sourceName);
        ctx.getEnvironment().getPropertySources().addFirst(ps);   //Add as the first source to override property-file
    }

}
