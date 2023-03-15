package ru.practicum.emw.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.practicum.emw"})
public class EwmMainServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(EwmMainServiceApp.class, args);
    }

}
