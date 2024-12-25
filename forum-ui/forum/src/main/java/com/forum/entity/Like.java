package com.forum.entity;

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
    private String targetType;
    private LocalDateTime createdAt;
} 