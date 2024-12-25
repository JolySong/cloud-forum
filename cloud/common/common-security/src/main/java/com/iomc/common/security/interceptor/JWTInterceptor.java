package com.iomc.common.security.interceptor;

import com.iomc.common.core.utils.StrUtils;
import com.iomc.common.security.utils.JWTUtils;
import com.iomc.common.security.utils.SecurityUtils;
import com.iomc.common.security.vo.LoginUserVO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * jwt验证处理拦截器(转化jwtToken存入ThreadLocal中)
 *
 */
@Component
public class JWTInterceptor implements HandlerInterceptor {

    /**
     *
     * 拦截器前置处理 添加用户信息
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String jwtToken = JWTUtils.getJwtToken(request);
        if (StrUtils.isNotBlank(jwtToken)) {
            LoginUserVO userInfo = JWTUtils.getUserInfo(jwtToken);
            SecurityUtils.setCurrentUser(userInfo);
        }

        // 放行请求
        return true;
    }
}