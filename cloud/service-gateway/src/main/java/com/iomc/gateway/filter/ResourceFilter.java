package com.iomc.gateway.filter;

import com.iomc.common.core.exception.BizException;
import com.iomc.common.core.exception.ExceptionEnum;
import com.iomc.common.core.utils.StrUtils;
import com.iomc.common.security.constant.Const;
import com.iomc.common.security.vo.ResourceVO;
import com.iomc.gateway.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;


/**
 * 资源过滤器优先级：1
 */
@Component
@Slf4j
public class ResourceFilter implements GlobalFilter, Ordered {


    /**
     * 登录放行
     */
    private static final String IGNORE_URL = "/auth/";

    public static final String REQUEST_PREFIX = "/api";

    /**
     * 过滤器
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String url = exchange.getRequest().getURI().getPath();
        // 放行权限认证相关接口
        if (StrUtils.isBlank(url) || url.contains(IGNORE_URL)) {
            return chain.filter(exchange);
        }

        // 放行公共资源
        if (ResourceService.isPublicResource(REQUEST_PREFIX + url)) {
            return chain.filter(exchange);
        }

        String requestUserId = exchange.getRequest().getHeaders().getFirst(Const.REQUEST_USER_ID);
        // 未登陆
        if (StrUtils.isBlank(requestUserId)) {
            log.error("AuthGlobalFilter.filter() 没有权限访问: {}", url);
            throw new BizException(ExceptionEnum.UNAUTHORIZED_ACCESS);
        }

        // 获取请求方式
        String method = Objects.requireNonNull(exchange.getRequest().getMethod()).toString();
        ResourceVO requestResource = new ResourceVO(REQUEST_PREFIX + url, method);

        // 匹配用户权限 todo 暂时取消权限校验
//        boolean b = ResourceService.matchUserResources(requestUserId, requestResource);
//        if (!b) {
//            log.error("AuthGlobalFilter.filter() 没有权限访问: {}", url);
//            throw new BizException(ExceptionEnum.UNAUTHORIZED_ACCESS);
//        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
