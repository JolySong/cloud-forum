package com.iomc.gateway.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iomc.common.core.Res;
import com.iomc.common.core.exception.BizException;
import com.iomc.common.core.exception.ExceptionEnum;
import com.iomc.common.core.utils.StrUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * gateway异常处理
 */
@Component
@Slf4j
@Order(-1)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomGlobalExceptionHandler implements ErrorWebExceptionHandler {

        private final ObjectMapper objectMapper;

        /**
         * @param exchange
         * @param ex
         * @return
         */
        @Override
        public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
            ServerHttpResponse response = exchange.getResponse();
            if (response.isCommitted()) {
                return Mono.error(ex);
            }

            String msg = StrUtils.isNotBlank(ex.getMessage())
                    ? ex.getMessage() : ExceptionEnum.INTERNAL_ERROR.getExceptionMsg();
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            BizException bizException = new BizException(ex.getMessage());
            if (ex instanceof BizException) {
                bizException = (BizException) ex;
                response.setStatusCode(HttpStatus.valueOf(bizException.getExceptionCode()));
            }
            if (msg.contains("404")) {
                bizException = new BizException(ExceptionEnum.RESOURCE_NOT_FOUND);
            }

            BizException finalBizException = bizException;
            return response.writeWith(Mono.fromSupplier(() -> {
                DataBufferFactory bufferFactory = response.bufferFactory();
                try {
                    //返回响应结果
                    return bufferFactory.wrap(
                            objectMapper.writeValueAsBytes(
                                    Res.fail(finalBizException.getExceptionCode(), finalBizException.getExceptionMsg())));
                }
                catch (JsonProcessingException e) {
                    log.error("Error writing response", ex);
                    return bufferFactory.wrap(new byte[0]);
                }
            }));
        }
    }