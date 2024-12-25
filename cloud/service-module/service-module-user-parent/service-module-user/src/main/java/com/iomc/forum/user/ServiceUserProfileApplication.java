package com.iomc.forum.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * 用户模块
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {
        "com.iomc.forum.topic.api.feign",
        "com.iomc.forum.analysis.api.feign",
        "com.iomc.forum.file.api.feign"
})
public class ServiceUserProfileApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUserProfileApplication.class, args);
    }
} 