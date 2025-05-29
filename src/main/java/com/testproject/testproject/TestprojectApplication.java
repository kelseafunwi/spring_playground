package com.testproject.testproject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestprojectApplication {

    private static final Logger log = LoggerFactory.getLogger(TestprojectApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TestprojectApplication.class, args);
    }
}