package com.iomc.common.core.global;

import com.iomc.common.core.Res;
import com.iomc.common.core.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

/**
 * 全局异常处理器
 *
 * @author forum
 * @since 2024-03-15
 */
@Slf4j
@Order(-1)
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(value = BizException.class)
    public Res<Void> handleBusinessException(BizException e) {
        log.info("handleBusinessException:[exception:{}]", e.getMessage());
        return Res.fail(e.getMessage());
    }


    /**
     * 默认的异常处理
     *
     * @param exception exception
     * @return res
     */
    @ExceptionHandler(value = Exception.class)
    public Res<String> exceptionHandler(Exception exception) {
        log.error("exceptionHandler:[exception:{}]", exception.getMessage());
        if (Objects.nonNull(exception.getMessage())) {
            return Res.fail(exception.getMessage());
        }
        return Res.fail(Res.ResEnum.INTERNAL_SERVER_ERROR.msg);
    }

}