package com.erick.udemy;

import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class HelloWorldService {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldService.class);

    @Value("${hello.service.greeting}")
    private String greeting;

    @Property(name = "hello.service.name", defaultValue = "guys")
    private String name;

    @EventListener
    public void init(StartupEvent startupEvent){
        LOG.debug("Startup: {}", HelloWorldService.class.getSimpleName());
    }

    public String sayHi(){
        LOG.info("chamando o método sayHi");
        return greeting + " " + name;
    }
}
