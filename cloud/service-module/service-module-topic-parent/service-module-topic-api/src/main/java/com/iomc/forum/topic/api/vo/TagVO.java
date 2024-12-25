package com.iomc.forum.topic.api.vo;


import lombok.Data;


/**
 * 标签
 */
@Data
public class TagVO {

    /**
     * 标签ID
     */
    private Long id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签颜色
     */
    private String color;

    /**
     * 标签使用数量
     */
    private Integer count;
}
