package com.mzik.laba5;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class BusDemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(BusDemoApplication.class, args);
    }

}
