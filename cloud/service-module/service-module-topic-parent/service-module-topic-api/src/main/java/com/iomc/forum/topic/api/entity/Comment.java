package com.iomc.forum.topic.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论实体
 */
@Data
@TableName("comments")
public class Comment {
    /**
     * 主键ID，自动生成
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 发布评论的用户ID
     */
    private Long userId;

    /**
     * 被评论的主题ID
     */
    private Long topicId;

    /**
     * 是否置顶（1: 是, 0: 否）
     */
    private Integer isTop;

    /**
     * 父评论ID，用于构建评论树结构
     */
    private Long parentId;

    /**
     * 回复的目标用户ID
     */
    private Long replyTo;

    /**
     * 评论状态（例如：0: 已删除, 1: 已发布）
     */
    private Integer status;

    /**
     * 审核状态（0: 待审核, 1: 已通过, 2: 已拒绝）
     */
    private Integer audit;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
