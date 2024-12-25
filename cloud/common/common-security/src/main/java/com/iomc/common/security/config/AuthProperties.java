package com.iomc.common.security.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthProperties implements InitializingBean {

    /**
     * 登录失效时间
     */
    @Value("${admin.auth.expire:604800}")
    public Integer expire;

    /**
     * jwt签名
     */
    @Value("${admin.auth.signature:key}")
    public String signature;


    public static Integer EXPIRE;

    public static String SIGNATURE;

    @Override
    public void afterPropertiesSet() throws Exception {
        EXPIRE = expire;
        SIGNATURE = signature;
    }

}
