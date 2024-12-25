package com.forum.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 收藏
 */
@Data
@TableName("collects")
public class Collect {
    private Long id;
    private Long userId;
    private Long topicId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
