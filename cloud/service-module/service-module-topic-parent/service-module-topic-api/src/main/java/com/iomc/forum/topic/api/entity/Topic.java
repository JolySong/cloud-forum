package com.iomc.forum.topic.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 话题表
 */
@Data
@TableName("topics")
public class Topic {
    /**
     * 主键ID，自动生成
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 主题标题
     */
    private String title;

    /**
     * 主题内容
     */
    private String content;

    /**
     * 发布用户的ID
     */
    private Long userId;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 是否为精选主题（true: 是, false: 否）
     */
    private Boolean isFeatured;

    /**
     * 评论状态（例如：0: 已删除, 1: 已发布）
     */
    private Integer status;

    /**
     * 审核状态（0: 待审核, 1: 已通过, 2: 已拒绝）
     */
    private Integer audit;

    /**
     * 是否置顶（true: 是, false: 否）
     */
    private Boolean isTop;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 点赞次数
     */
    private Integer likeCount;

    /**
     * 收藏次数
     */
    private Integer collectCount;

    /**
     * 评论次数
     */
    private Integer commentCount;

    /**
     * 最后一次评论的时间
     */
    private LocalDateTime lastCommentAt;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
