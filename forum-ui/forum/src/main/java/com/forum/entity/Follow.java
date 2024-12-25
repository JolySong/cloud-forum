package com.forum.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 关注实体类
 */
@Data
@TableName("follows")
public class Follow {
    private Long id;
    private Long userId;
    private Long followedId;
    private LocalDateTime createdAt;
} 