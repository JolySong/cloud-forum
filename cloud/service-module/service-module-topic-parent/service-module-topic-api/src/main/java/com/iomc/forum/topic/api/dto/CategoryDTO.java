package com.iomc.forum.topic.api.dto;

import lombok.Data;

@Data
public class CategoryDTO {

    /** 主键 */
    private Long id;
    /** 分类名 */
    private String name;
    /** 分类描述 */
    private String description;
    /** 排序 */
    private Long sort;
    /** 状态 */
    private Integer status;
}
