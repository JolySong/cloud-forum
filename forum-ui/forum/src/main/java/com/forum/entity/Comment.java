package com.forum.entity;

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
    @TableId(type = IdType.AUTO)
    private Long id;
    private String content;
    private Long userId;
    private Long topicId;
    private Long parentId;
    private Long replyTo;
    private Integer likeCount;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 