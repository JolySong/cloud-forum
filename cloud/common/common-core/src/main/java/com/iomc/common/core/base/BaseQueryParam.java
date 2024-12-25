package com.iomc.common.core.base;


import lombok.Data;

@Data
public class BaseQueryParam {

    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 每页数量
     */
    private Integer size;

    /**
     * 关键词
     */
    private String keyword;
}
