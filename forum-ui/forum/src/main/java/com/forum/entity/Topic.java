package com.forum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("topics")
public class Topic {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Long categoryId;
    private Integer status;
    private Boolean isFeatured;
    private Boolean isTop;
    private Integer viewCount;
    private Integer likeCount;
    private Integer collectCount;
    private Integer commentCount;
    private LocalDateTime lastCommentAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 