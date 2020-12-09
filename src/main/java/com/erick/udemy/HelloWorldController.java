package com.erick.udemy;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("${hello.controller.path}")
public class HelloWorldController {

    private final HelloWorldService service;
    private final GreetingConfig config;

    public HelloWorldController(HelloWorldService service, GreetingConfig config) {
        this.service = service;
        this.config = config;
    }

    @Get("/")
    public String index(){
        return this.service.sayHi();
    }

    @Get("/de")
    public String greetInGerman(){
        return config.getDe();
    }

    @Get("/en")
    public String greetInEnglish(){
        return config.getEn();
    }
}
