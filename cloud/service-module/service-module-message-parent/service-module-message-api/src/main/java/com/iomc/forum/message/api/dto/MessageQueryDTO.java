package com.iomc.forum.message.api.dto;

import lombok.Data;

/**
 * 消息查询DTO
 *
 * @author forum
 * @since 2024-12-25
 */
@Data
public class MessageQueryDTO {
    
    /**
     * 当前页码
     */
    private Integer page = 1;
    
    /**
     * 每页大小
     */
    private Integer size = 10;
    
    /**
     * 消息类型 (like:点赞, comment:评论, system:系统通知)
     */
    private String type;
    
    /**
     * 是否已读 (true:已读, false:未读, null:全部)
     */
    private Boolean isRead;
} 