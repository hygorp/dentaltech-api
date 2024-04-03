package com.dentaltechapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DentaltechApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DentaltechApiApplication.class, args);
    }

}
