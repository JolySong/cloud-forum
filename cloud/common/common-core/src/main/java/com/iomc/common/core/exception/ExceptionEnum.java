package com.iomc.common.core.exception;

public enum ExceptionEnum implements CommonException {

    /**
     * 所有的代码内部错误都抛出该异常
     */
    INTERNAL_ERROR(500, "内部服务异常"),

    /**
     * 禁止访问抛出该异常
     */
    FORBIDDEN(403, "禁止访问"),

    /**
     * 资源不存在抛出该异常
     */
    RESOURCE_NOT_FOUND(404, "资源不存在"),

    /**
     * 文件异常
     */
    FILE_ERROR(600, "文件异常"),

    /**
     * 未经权限访问抛出该异常
     */
    UNAUTHORIZED_ACCESS(401, "未经授权访问"),
    /**
     * token过期抛出该异常
     */
    TOKEN_EXPIRED(402, "token校验失败或已过期"),
    /**
     * 用户未登录抛出该异常
     */
    UNAUTHORIZED(403, "未登陆"),

    /**
     * 如参数校验未通过，参数为空等抛出该异常
     */
    PARAMS_ERROR(400, "参数异常"),

    /**
     * 对爬虫等恶意访问抛出该异常
     */
    REQUEST_RATE_LIMIT(300, "请求超过速率");




    private int exCode;
    private String exMsg;

    ExceptionEnum(int exCode, String exMsg) {
        this.exCode = exCode;
        this.exMsg = exMsg;
    }

    @Override
    public int getExceptionCode() {
        return exCode;
    }

    @Override
    public String getExceptionMsg() {
        return exMsg;
    }
}
