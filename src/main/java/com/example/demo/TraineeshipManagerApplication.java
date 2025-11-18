package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "traineeship_manager.domainmodel")
@EnableJpaRepositories(basePackages = "traineeship_manager.repository")
@ComponentScan(basePackages = "traineeship_manager")
public class TraineeshipManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TraineeshipManagerApplication.class, args);
    }
}