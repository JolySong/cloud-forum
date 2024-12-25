package com.iomc.common.security.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

/**
 * feign拦截器
 */
@Slf4j
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 1. obtain request
        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        // 2. 兼容hystrix限流后，获取不到ServletRequestAttributes的问题（使拦截器直接失效）
        if (Objects.isNull(attributes)) {
            log.error("MyFeignRequestInterceptor is invalid!");
            return;
        }
        HttpServletRequest request = attributes.getRequest();

        // 2. obtain request headers，and put it into openFeign RequestTemplate
        Enumeration<String> headerNames = request.getHeaderNames();
        if (Objects.nonNull(headerNames)) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = request.getHeader(name);
                requestTemplate.header(name, value);
            }
        }

        // 3. obtain request body, and put it into openFeign RequestTemplate
        Enumeration<String> bodyNames = request.getParameterNames();
        StringBuffer body = new StringBuffer();
        if (bodyNames != null) {
            while (bodyNames.hasMoreElements()) {
                String name = bodyNames.nextElement();
                String value = request.getParameter(name);
                body.append(name).append("=").append(value).append("&");
            }
        }
        if (body.length() != 0) {
            body.deleteCharAt(body.length() - 1);
            requestTemplate.body(body.toString());
            log.info("openfeign interceptor body:{}", body.toString());
        }
    }
}
