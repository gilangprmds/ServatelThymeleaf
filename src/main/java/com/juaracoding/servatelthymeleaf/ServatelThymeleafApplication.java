package com.juaracoding.servatelthymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ServatelThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServatelThymeleafApplication.class, args);
    }

}
