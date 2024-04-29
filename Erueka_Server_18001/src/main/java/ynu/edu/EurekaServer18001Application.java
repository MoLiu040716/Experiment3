package ynu.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServer18001Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer18001Application.class, args);
    }

}
