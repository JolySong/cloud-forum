package com.iomc.forum.topic.api.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryVO {

    /** 主键 */
    private Long id;
    /** 分类名 */
    private String name;
    /** 分类描述 */
    private String description;
    /** 排序 */
    private Integer sort;
    /** 状态 */
    private Integer status;
    /** 文章数量 */
    private Integer topicCount;
    /** 查看次数 */
    private Integer totalViews;
    /** 回复数量 */
    private Integer totalComments;
    /** 点赞数量 */
    private Integer totalLikes;
    /** 创建时间 */
    private LocalDateTime createdAt;
} 