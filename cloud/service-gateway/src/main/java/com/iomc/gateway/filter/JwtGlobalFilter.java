package com.iomc.gateway.filter;

import com.iomc.common.core.utils.StrUtils;
import com.iomc.common.security.constant.Const;
import com.iomc.common.security.utils.JWTUtils;
import com.iomc.common.security.vo.LoginUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 *
 * JwtToken校验过滤器优先级: 0
 *
 */
@Component
@Slf4j
public class JwtGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 过滤器
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String jwtToken = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StrUtils.isBlank(jwtToken)) {
            log.info("AuthGlobalFilter.filter() 未登陆进入下一级过滤器判断是否公共资源");
            return chain.filter(exchange);
        }

        String jwtTokenParse = jwtToken.replace("Bearer ", "");
        JWTUtils.verify(jwtTokenParse);

        LoginUserVO userInfo = JWTUtils.getUserInfo(jwtTokenParse);
        // 添加请求头代表验证通过
        exchange.getRequest()
                .mutate()
                .header(Const.REQUEST_USER_ID, String.valueOf(userInfo.getId()))
                .header(Const.JWT_PAYLOAD_TOKEN, JWTUtils.getPayloadToken(jwtTokenParse))
                .build();

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
