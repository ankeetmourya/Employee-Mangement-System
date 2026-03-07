package com.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class EmsRegisterationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmsRegisterationApplication.class, args);
    }

}
