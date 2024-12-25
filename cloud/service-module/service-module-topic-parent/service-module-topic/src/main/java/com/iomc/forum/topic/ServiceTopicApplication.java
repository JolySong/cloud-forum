package com.iomc.forum.topic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * 话题模块
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {
        "com.iomc.forum.user.api.feign",
        "com.iomc.forum.analysis.api.feign"
})
public class ServiceTopicApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceTopicApplication.class, args);
    }
} 