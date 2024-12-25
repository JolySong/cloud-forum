package com.iomc.common.core.exception;

public class BizException extends RuntimeException implements CommonException {

    /**
     * 异常码
     */
    private int exCode;

    /**
     * 异常信息
     */
    private String exMsg;

    @Override
    public int getExceptionCode() {
        return exCode;
    }

    @Override
    public String getExceptionMsg() {
        return exMsg;
    }

    /**
     * 使用自定义的异常枚举类构造异常
     * @param exceptionEnum
     */
    public BizException(CommonException exceptionEnum){

        this.exCode = exceptionEnum.getExceptionCode();
        this.exMsg = exceptionEnum.getExceptionMsg();
    }

    public BizException(int exCode, String exMsg){
        this.exCode = exCode;
        this.exMsg = exMsg;
    }

    public BizException(int exCode){
        this.exCode = exCode;
        this.exMsg = "未知错误";
    }

    public BizException(String exMsg){
        this.exCode = 10001;
        this.exMsg = exMsg;
    }

}
