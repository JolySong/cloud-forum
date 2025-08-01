package com.iomc.forum.message.api.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消息VO
 *
 * @author forum
 * @since 2024-12-25
 */
@Data
public class MessageVO {
    
    /**
     * 消息ID
     */
    private Long id;
    
    /**
     * 消息类型 (like:点赞, comment:评论, system:系统通知)
     */
    private String type;
    
    /**
     * 消息内容
     */
    private String content;
    
    /**
     * 发送者用户ID
     */
    private Long fromUserId;
    
    /**
     * 发送者用户名
     */
    private String fromUserName;
    
    /**
     * 发送者头像
     */
    private String fromUserAvatar;
    
    /**
     * 目标类型 (topic:话题, comment:评论)
     */
    private String targetType;
    
    /**
     * 目标ID
     */
    private Long targetId;
    
    /**
     * 话题ID
     */
    private Long topicId;
    
    /**
     * 是否已读
     */
    private Boolean isRead;
    
    /**
     * 消息状态
     */
    private Boolean status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 引用内容
     */
    private MessageQuoteVO quote;
} 