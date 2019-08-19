package com.example.starterapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@EnableCaching
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.starterapp"})
@MapperScan("com.example.starterapp.mapper")
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class StarterappApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarterappApplication.class, args);
    }

}
