# dynamic-spring-configuration
Zookeeper as Dynamic PropertySource for Spring app

##Summary
This is a simple Spring app demonstrating the use of Zookeeper as the property source/config store.

##Usage
1. Run with command *mvn spring-boot:run*
2. Observe console to see scheduler print the name property evey 5 sec.
3. You can also invoke GET: /service/{property} to get the value of the property from zookeeper.
4. If you shutdown zookeeper, app will fall back to cached value or the properties in the files.

##Libraries
Apache Zookeeper  
Apache Curator  
Archaius  
Spring Cloud  

##References
1. https://github.com/Netflix/archaius/wiki/ZooKeeper-Dynamic-Configuration
2. https://github.com/spring-cloud/spring-cloud-config
3. http://www.java-allandsundry.com/2015/03/netflix-archaius-properties-in-spring.html
4. http://stackoverflow.com/questions/11925952/custom-spring-property-source-does-not-resolve-placeholders-in-value