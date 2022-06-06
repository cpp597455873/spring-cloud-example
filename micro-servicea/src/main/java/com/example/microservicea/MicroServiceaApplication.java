package com.example.microservicea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.example.microserviceb"})
public class MicroServiceaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceaApplication.class, args);
    }

}
