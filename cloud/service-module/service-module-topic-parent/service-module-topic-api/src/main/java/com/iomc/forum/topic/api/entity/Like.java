package com.iomc.forum.topic.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 点赞
 */
@Data
@TableName("likes")
public class Like {
    private Long id;
    private Long userId;
    private Long targetId;
    /**
     * 0:主题, 1:评论
     */
    private Integer targetType;
    private LocalDateTime createdAt;
} 