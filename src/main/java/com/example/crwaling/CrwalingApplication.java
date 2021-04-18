package com.example.crwaling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrwalingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrwalingApplication.class, args);
    }

}
