package com.task.group.ignitis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class IgnitisApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(IgnitisApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(IgnitisApplication.class);
    }

}
