package com.iomc.common.core.global;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iomc.common.core.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一返回值封装
 *
 * @author forum
 * @since 2024-03-15
 */
@ControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object>  {


    @Autowired
    private ObjectMapper objectMapper;

    /**
     *
     * 支持所有返回值
     *
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     *
     * 统一返回值
     *
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        // 如果Controller返回String的话，SpringBoot不会帮我们自动封装而直接返回，因此我们需要手动转换成json。
        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(Res.success(body));
            } catch (JsonProcessingException e) {
                return Res.fail(e.getMessage());
            }
        }
        // 如果返回的结果是R对象，即已经封装好的，直接返回即可。
        // 如果不进行这个判断，后面进行全局异常处理时会出现错误
        if (body instanceof Res) {
            return body;
        }
        return Res.success(body);
    }

}
