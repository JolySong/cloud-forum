package com.iomc.forum.message.api.vo;

import lombok.Data;

/**
 * 消息引用VO
 *
 * @author forum
 * @since 2024-12-25
 */
@Data
public class MessageQuoteVO {
    
    /**
     * 引用标题
     */
    private String title;
    
    /**
     * 引用内容
     */
    private String content;
    
    /**
     * 引用类型 (topic:话题, comment:评论)
     */
    private String type;
} 