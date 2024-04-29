package com.example.eureka_server_18000;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServer18000Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer18000Application.class, args);
    }

}
