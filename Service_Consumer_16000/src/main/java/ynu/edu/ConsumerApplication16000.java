package ynu.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ynu.edu.rule.CustomLoadBalancerConfig;


@SpringBootApplication
@EnableFeignClients
@LoadBalancerClient(name="provider-server",configuration = CustomLoadBalancerConfig.class)
public class ConsumerApplication16000 {


    public static void main(String[] args) {

        SpringApplication.run(ConsumerApplication16000.class,args);
    }
}
