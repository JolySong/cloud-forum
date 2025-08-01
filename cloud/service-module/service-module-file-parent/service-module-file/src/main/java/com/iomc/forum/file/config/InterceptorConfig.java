package com.iomc.forum.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Value("${file.upload}")
    private String uploadPath;

    /**
     * 文件路径映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 适配windows
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            uploadPath = uploadPath.replace("/", "\\");
        } else {
            uploadPath = uploadPath.replace("\\", "/");
        }

        registry.addResourceHandler("/files/**")
                .addResourceLocations("classpath:" + uploadPath);
    }
}