package com.iomc.forum.topic.api.dto;

import lombok.Data;

@Data
public class TagDTO {

    /**
     * 标签ID
     */
    private Long id;

    /**
     * 标签名
     */
    private String name;

    /**
     * 标签颜色
     */
    private String color;
}
