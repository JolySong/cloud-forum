package com.iomc.auth;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {
        "com.iomc.forum.user.api.feign"
})
public class ServiceAuthApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(ServiceAuthApplication.class, args);
    }
}
